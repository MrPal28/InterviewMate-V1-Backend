package com.interviewmate.codingservice.service.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.interviewmate.codingservice.EventController.SubmissionEventProducer;
import com.interviewmate.codingservice.config.SubmissionStreamManager;
import com.interviewmate.codingservice.constants.LanguageId;
import com.interviewmate.codingservice.constants.SubmissionStatus;
import com.interviewmate.codingservice.dto.CreateSubmissionRequest;
import com.interviewmate.codingservice.dto.JudgeTestCaseDto;
import com.interviewmate.codingservice.dto.RunWithEmitter;
import com.interviewmate.codingservice.dto.SubmissionResponse;
import com.interviewmate.codingservice.dto.SubmissionWithEmitter;
import com.interviewmate.codingservice.entity.Submission;
import com.interviewmate.codingservice.entity.TestCase;
import com.interviewmate.codingservice.events.SubmissionCreatedEventDto;
import com.interviewmate.codingservice.repository.ProblemRepository;
import com.interviewmate.codingservice.repository.SubmissionRepository;
import com.interviewmate.codingservice.service.SubmissionService;
import com.interviewmate.codingservice.service.TestCaseService;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private static final Logger log = LoggerFactory.getLogger(SubmissionServiceImpl.class);

    private final SubmissionRepository submissionRepository;
    private final SubmissionEventProducer submissionEventProducer;
    private final SubmissionStreamManager submissionStreamManager;
    private final TestCaseService testCaseService;

    public SubmissionServiceImpl(
            SubmissionRepository submissionRepository,
            SubmissionEventProducer submissionEventProducer,
            SubmissionStreamManager submissionStreamManager,
            TestCaseService testCaseService,
            ProblemRepository problemRepository) {
        this.submissionRepository = submissionRepository;
        this.submissionEventProducer = submissionEventProducer;
        this.submissionStreamManager = submissionStreamManager;
        this.testCaseService = testCaseService;
    }

    @Override
    public SubmissionWithEmitter createSubmission(CreateSubmissionRequest submissionRequest, String userId) {

        Submission submission = Submission.builder()
                .problemId(submissionRequest.getProblemId())
                .userId(userId)
                .sourceCode(submissionRequest.getSourceCode())
                .status(SubmissionStatus.QUEUED)
                .language(submissionRequest.getLanguageId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // 1) Persist submission
        Submission saved = submissionRepository.save(submission);
        String submissionId = saved.getId();

        log.info("Created submission {} for user {}", submissionId, saved.getUserId());

        // 2) Create SSE channel
        SseEmitter emitter = submissionStreamManager.createStream(submissionId);

        // 3) SSE handshake event
        submissionStreamManager.send(submissionId, "CONNECTED", Map.of(
                "submissionId", submissionId,
                "message", "Stream initialized"));

        // 4) Notify user that job is queued
        submissionStreamManager.send(submissionId, "QUEUED", Map.of(
                "submissionId", submissionId,
                "status", "QUEUED",
                "message", "Submission queued for processing"));

        log.info("Submission {} queued, SSE stream open", submissionId);

        List<TestCase> testCases = testCaseService.getTestCasesForProblem(submissionRequest.getProblemId(), true);

        // 5) Publish event to Kafka → consumed by Judge Worker
        submissionEventProducer.publishCreatedEvent(convertToEventDto(saved, null ,testCases));

        log.info("Published submission {} to Kafka topic=‘submissions.created’", submissionId);

        return SubmissionWithEmitter.builder()
                .submissionId(submissionId)
                .emitter(emitter)
                .build();
    }
    @Override
    public RunWithEmitter createSubmissionDryRun(CreateSubmissionRequest submissionRequest, String executionId, String userId) {

        Submission submission = Submission.builder()
                .problemId(submissionRequest.getProblemId())
                .userId(userId)
                .sourceCode(submissionRequest.getSourceCode())
                .status(SubmissionStatus.QUEUED)
                .language(submissionRequest.getLanguageId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        log.info("Created submission {} for user {}", executionId, userId);

        // 2) Create SSE channel
        SseEmitter emitter = submissionStreamManager.createRunStream(executionId);

        // 3) SSE handshake event
        submissionStreamManager.send(executionId, "CONNECTED", Map.of(
                "executionId", executionId,
                "message", "Stream initialized"));

        // 4) Notify user that job is queued
        submissionStreamManager.send(executionId, "QUEUED", Map.of(
                "executionId", executionId,
                "status", "QUEUED",
                "message", "Submission queued for processing"));

        log.info("Submission {} queued, SSE stream open", executionId);

       
        
        List<TestCase> testCases = testCaseService.getTestCasesForProblem(submissionRequest.getProblemId(), false);

        // 5) Publish event to Kafka → consumed by Judge Worker
        submissionEventProducer.publishCreatedEvent(convertToEventDto(submission, executionId, testCases));

        log.info("Published submission {} to Kafka topic=‘submissions.created’", executionId);

        return RunWithEmitter.builder()
                .executionId(executionId)
                .emitter(emitter)
                .build();
    }

    @Override
    public Submission getSubmissionById(String id) {
        return submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
    }

    @Override
    public List<Submission> getSubmissionsForUser(String userId) {
        return submissionRepository.findByUserId(userId);
    }

    @Override
    public List<Submission> getSubmissionsForProblemAndUser(String problemId, String userId) {
        return submissionRepository.findByProblemIdAndUserId(problemId, userId);
    }

    private SubmissionCreatedEventDto convertToEventDto(
        Submission submission,
        String executionId,
        List<TestCase> testCases
       ) {

    LanguageId languageId; 
    try {
        languageId = LanguageId.valueOf(
                submission.getLanguage().toUpperCase());
    } catch (IllegalArgumentException ex) {
        throw new UnsupportedOperationException(
                "Unsupported language: " + submission.getLanguage());
    }

    List<JudgeTestCaseDto> judgeTestCases =
            fetchAndConvertToJudgeTestCaseDto(testCases);

    return SubmissionCreatedEventDto.builder()
            .submissionId(submission.getId()!= null ? submission.getId() : executionId)
            .userId(submission.getUserId())
            .problemId(submission.getProblemId())
            .languageId(mapToJudge0(languageId))
            .sourceCode(submission.getSourceCode())
            .testCases(judgeTestCases)
            .createdAt(submission.getCreatedAt())
            .build();
}


    private int mapToJudge0(LanguageId lang) {
        return switch (lang) {
            case JAVA -> 62;
            case PYTHON -> 71;
            case JAVASCRIPT -> 63;
            case CPP -> 54;
            case C -> 50;
            case CSHARP -> 51;
        };
    }

    private List<JudgeTestCaseDto> fetchAndConvertToJudgeTestCaseDto(
        List<TestCase> testCases) {

    return testCases.stream()
            .map(testCase -> JudgeTestCaseDto.builder()
                    .stdin(testCase.getStdin())     //  FIX
                    .stdout(testCase.getStdout())   //  FIX
                    .hidden(testCase.isHidden())
                    .build())
            .collect(Collectors.toList());
        }

    @Override
    public List<SubmissionResponse> getSubmissionResultByProblemId(String problemId, String userId) {
        List<Submission> submissions = submissionRepository.findByProblemIdAndUserId(problemId, userId);
        
        List<SubmissionResponse> submissionResponse = submissions.stream()
                .map(submission -> SubmissionResponse.builder()
                        .id(submission.getId())
                        .userId(submission.getUserId())
                        .problemId(submission.getProblemId())
                        .language(submission.getLanguage())
                        .status(submission.getStatus())
                        .sourceCode(submission.getSourceCode())
                        .status(submission.getStatus())
                        .stdout(submission.getVerdict())
                        .createdAt(submission.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
        return submissionResponse;
    }

    @Override
    public List<SubmissionResponse> getAllSubmissions(String userId) {
        List<Submission> submissions = submissionRepository.findByUserId(userId);
        
        List<SubmissionResponse> submissionResponse = submissions.stream()
                .map(submission -> SubmissionResponse.builder()
                        .id(submission.getId())
                        .userId(submission.getUserId())
                        .problemId(submission.getProblemId())
                        .language(submission.getLanguage())
                        .status(submission.getStatus())
                        .sourceCode(submission.getSourceCode())
                        .status(submission.getStatus())
                        .stdout(submission.getVerdict())
                        .createdAt(submission.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
        return submissionResponse;
    }

}
