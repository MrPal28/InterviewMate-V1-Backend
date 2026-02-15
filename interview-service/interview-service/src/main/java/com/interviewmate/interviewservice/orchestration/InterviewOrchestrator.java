package com.interviewmate.interviewservice.orchestration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.interviewmate.interviewservice.client.python.PythonInterviewClient;
import com.interviewmate.interviewservice.constants.InterviewStatus;
import com.interviewmate.interviewservice.dto.event.VideoAnalysisRequestEvent;
import com.interviewmate.interviewservice.dto.request.PythonInitInterviewRequest;
import com.interviewmate.interviewservice.dto.request.PythonSecondSlotRequest;
import com.interviewmate.interviewservice.dto.request.SecondSlotRequest;
import com.interviewmate.interviewservice.dto.request.StartInterviewRequest;
import com.interviewmate.interviewservice.dto.request.SubmitAnswerRequest;
import com.interviewmate.interviewservice.dto.response.QuestionDto;
import com.interviewmate.interviewservice.dto.response.StartInterviewResponse;
import com.interviewmate.interviewservice.dto.response.UserReportCache;
import com.interviewmate.interviewservice.dto.response.UserReportDto;
import com.interviewmate.interviewservice.entity.InterviewSession;
import com.interviewmate.interviewservice.entity.UserReportDocument;
import com.interviewmate.interviewservice.eventhandler.KafkaProducer;
import com.interviewmate.interviewservice.repository.mongorepo.UserReportMongoRepository;
import com.interviewmate.interviewservice.repository.redisrepo.InterviewSessionRepository;
import com.interviewmate.interviewservice.repository.redisrepo.UserReportRedisRepository;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterviewOrchestrator {

        private final PythonInterviewClient pythonInterviewClient;
        private final InterviewSessionRepository sessionRepository;
        private final KafkaProducer kafkaProducer;
        private final UserReportRedisRepository userReportRedisRepository;
        private final UserReportMongoRepository userReportMongoRepository;

        public Mono<StartInterviewResponse> startInterview(
                        StartInterviewRequest request,
                        String userId) {

                // Build request for Python AI service
                PythonInitInterviewRequest pythonRequest = PythonInitInterviewRequest.builder()
                                .userid(userId)
                                .resumeurl(request.getResumeUrl())
                                .specificquestionrequirement(request.isSpecificQuestionRequirement())
                                .subjectortopic(request.getSubjectOrTopic())
                                .numberofquestions(request.getNumberOfQuestions())
                                .level(request.getLevel())
                                .build();

                // Call Python service (blocking only this step)
                return pythonInterviewClient.getFirstSlotQuestions(pythonRequest)
                                .flatMap(pythonResponse -> {

                                        Instant now = Instant.now();

                                        // Initialize COMPLETE interview session state
                                        InterviewSession session = InterviewSession.builder()
                                                        // Identifiers
                                                        .sessionId(pythonResponse.getSessionid())
                                                        .userId(userId)

                                                        // Interview configuration
                                                        .resumeUrl(request.getResumeUrl())
                                                        .specificQuestionRequirement(
                                                                        request.isSpecificQuestionRequirement())
                                                        .subjectOrTopic(request.getSubjectOrTopic())
                                                        .totalQuestions(pythonResponse.getNumberofquestions())
                                                        .level(request.getLevel())

                                                        // Workflow state
                                                        .currentQuestion(1)
                                                        .remainingQuestions(pythonResponse.getRemanning())
                                                        .slotOneFetched(true)
                                                        .slotTwoFetched(false)
                                                        .status(InterviewStatus.IN_PROGRESS)

                                                        // Questions (source of truth)
                                                        .questions(pythonResponse.getSlotonequestions())

                                                        // Audit
                                                        .createdAt(now)
                                                        .updatedAt(now)
                                                        .build();

                                        // Persist to Redis (Blocking Repository wrap)
                                        return Mono.fromCallable(() -> sessionRepository.save(session))
                                                        .subscribeOn(Schedulers.boundedElastic())
                                                        .map(savedSession -> StartInterviewResponse.builder()
                                                                        .userId(userId)
                                                                        .sessionId(savedSession.getSessionId())
                                                                        .questions(mapQuestions(
                                                                                        savedSession.getQuestions()))
                                                                        .remaining(savedSession.getRemainingQuestions())
                                                                        .totalQuestions(savedSession
                                                                                        .getTotalQuestions())
                                                                        .build());
                                });
        }

        private List<QuestionDto> mapQuestions(List<String> questions) {
                return IntStream.range(0, questions.size())
                                .mapToObj(i -> new QuestionDto(i + 1, questions.get(i)))
                                .toList();
        }

        public Mono<StartInterviewResponse> fetchSecondSlot(SecondSlotRequest request , String userId) {

                return Mono.fromCallable(() -> sessionRepository.findById(request.getSessionId()))
                                .subscribeOn(Schedulers.boundedElastic())
                                .flatMap(optionalSession -> Mono.justOrEmpty(optionalSession))
                                .switchIfEmpty(Mono.error(
                                                new IllegalStateException("Interview session not found")))
                                .flatMap(session -> {

                                        /*
                                         * =========================
                                         * VALIDATE SESSION
                                         * =========================
                                         */

                                        if (session.getStatus() != InterviewStatus.IN_PROGRESS) {
                                                return Mono.error(
                                                                new IllegalStateException(
                                                                                "Interview is not in progress"));
                                        }

                                        if (!session.getUserId().equals(userId)) {
                                                return Mono.error(
                                                                new IllegalStateException(
                                                                                "User does not own this session"));
                                        }

                                        /*
                                         * =========================
                                         * RETURN CACHED SLOT
                                         * =========================
                                         */

                                        if (session.isSlotTwoFetched()) {
                                                return Mono.just(buildResponse(session));
                                        }

                                        /*
                                         * =========================
                                         * CALL PYTHON FOR SLOT 2
                                         * =========================
                                         */

                                        PythonSecondSlotRequest pythonRequest = PythonSecondSlotRequest.builder()
                                                        .userid(userId)
                                                        .sessionid(session.getSessionId())
                                                        .remanning(session.getRemainingQuestions())
                                                        .level(session.getLevel())
                                                        .build();

                                        return pythonInterviewClient.getSecondSlotQuestions(pythonRequest)
                                                        .flatMap(pythonResponse -> {

                                                                // Python returned slot-two questions
                                                                List<String> slotTwoQuestions = pythonResponse.getSlottwoquestions();
                                                                session.getQuestions()
                                                                                .addAll(slotTwoQuestions);

                                                                session.setRemainingQuestions(0);
                                                                session.setSlotTwoFetched(true);
                                                                session.setUpdatedAt(Instant.now());

                                                                return Mono.fromCallable(
                                                                                () -> sessionRepository.save(session))
                                                                                .subscribeOn(Schedulers
                                                                                                .boundedElastic())
                                                                                .map(savedSession -> buildSecondSlotResponse(savedSession, slotTwoQuestions));
                                        
                                                        })
                                                        /*
                                                         * =========================
                                                         * HANDLE 202 ACCEPTED
                                                         * =========================
                                                         */
                                                        .onErrorResume(WebClientResponseException.class, ex -> {
                                                                if (ex.getStatusCode() == HttpStatus.ACCEPTED) {
                                                                        return Mono.error(new ResponseStatusException(
                                                                                        HttpStatus.ACCEPTED,
                                                                                        "Second slot is still being processed"));
                                                                }
                                                                return Mono.error(ex);
                                                        });
                                });
        }
     
        private StartInterviewResponse buildSecondSlotResponse(
                        InterviewSession session,
                        List<String> slotTwoQuestions) {
                
                return StartInterviewResponse.builder()
                                .userId(session.getUserId())
                                .sessionId(session.getSessionId())
                                .questions(mapQuestions(slotTwoQuestions)) // only second slot
                                .remaining(session.getRemainingQuestions())
                                .totalQuestions(session.getTotalQuestions())
                                .build();
        }

        private StartInterviewResponse buildResponse(InterviewSession session) {

                List<QuestionDto> questionDtos = IntStream.range(0, session.getQuestions().size())
                                .mapToObj(i -> new QuestionDto(i + 1, session.getQuestions().get(i)))
                                .toList();

                return StartInterviewResponse.builder()
                                .userId(session.getUserId())
                                .sessionId(session.getSessionId())
                                .questions(questionDtos)
                                .remaining(session.getRemainingQuestions())
                                .totalQuestions(session.getTotalQuestions())
                                .build();
        }

        public Mono<Void> submitAnswer(
                        SubmitAnswerRequest request,
                        String userIdFromHeader) {

                return Mono.fromCallable(() -> sessionRepository.findById(request.getSessionId()))
                                .subscribeOn(Schedulers.boundedElastic())
                                .flatMap(optionalSession -> Mono.justOrEmpty(optionalSession))
                                .switchIfEmpty(Mono.error(
                                                new IllegalStateException("Interview session not found")))
                                .flatMap(session -> {

                                        /*
                                         * =========================
                                         * VALIDATE SESSION STATE
                                         * =========================
                                         */

                                        if (session.getStatus() != InterviewStatus.IN_PROGRESS) {
                                                return Mono.error(
                                                                new IllegalStateException(
                                                                                "Interview is not in progress"));
                                        }

                                        if (!session.getUserId().equals(userIdFromHeader)) {
                                                return Mono.error(
                                                                new IllegalStateException(
                                                                                "User does not own this session"));
                                        }

                                        if (request.getQuestionNo() != session.getCurrentQuestion()) {
                                                return Mono.error(
                                                                new IllegalStateException("Invalid question order"));
                                        }

                                        /*
                                         * =========================
                                         * BUILD KAFKA EVENT
                                         * =========================
                                         */

                                        VideoAnalysisRequestEvent event = VideoAnalysisRequestEvent.builder()
                                                        .userid(userIdFromHeader)
                                                        .sessionid(session.getSessionId())
                                                        .questionno(request.getQuestionNo())
                                                        .question(request.getQuestion())
                                                        .videourl(request.getVideoUrl())
                                                        .totalnumberofquestion(request.getTotalNumberOfQuestion())
                                                        .build();

                                        /*
                                         * =========================
                                         * PUBLISH TO KAFKA
                                         * =========================
                                         */

                                        kafkaProducer.publish(event);

                                        /*
                                         * =========================
                                         * UPDATE SESSION STATE
                                         * =========================
                                         */

                                        session.setCurrentQuestion(session.getCurrentQuestion() + 1);
                                        session.setRemainingQuestions(
                                                        Math.max(0, session.getRemainingQuestions() - 1));
                                        session.setUpdatedAt(Instant.now());

                                        return Mono.fromCallable(() -> sessionRepository.save(session))
                                                        .subscribeOn(Schedulers.boundedElastic())
                                                        .then();
                                });
        }

public Mono<List<UserReportDto>> getUserReport(String userId) {

    if (userId == null || userId.isBlank()) {
        return Mono.error(new IllegalArgumentException("User ID is required"));
    }

    return Mono.fromCallable(() ->
                    userReportRedisRepository.findAllByUserId(userId)
            )
            .subscribeOn(Schedulers.boundedElastic())
            .flatMap(cachedReports -> {

                //  Cache hit
                if (cachedReports != null && !cachedReports.isEmpty()) {
                    log.info("Cache hit for user ID: {}", userId);
                    return Mono.just(
                            cachedReports.stream()
                                    .map(UserReportCache::getReport)
                                    .toList()
                    );
                }

                //  Cache miss → Mongo
                log.info("Cache miss for user ID: {}", userId);
                return userReportMongoRepository.findAllByUserId(userId)
                        .map(this::convertToUserReportDto)   
                        .collectList()
                        .flatMap(reports -> {

                            if (reports.isEmpty()) {
                                return Mono.<List<UserReportDto>>empty(); // explicittype
                            }

                            //  Cache update (non-blocking, best-effort)
                            return Mono.fromRunnable(() ->
                                    userReportRedisRepository.saveAll(
                                            reports.stream()
                                                    .map(UserReportCache::from)
                                                    .toList()
                                    )
                                
                            )
                            .subscribeOn(Schedulers.boundedElastic())
                            .thenReturn(reports);
                        });
            })
            .switchIfEmpty(
                    Mono.error(new IllegalArgumentException("User reports not found"))
            );
}



        public UserReportDto convertToUserReportDto(UserReportDocument userReportDocument) {
                return UserReportDto.builder()
                                .sessionId(userReportDocument.getSessionId())
                                .userId(userReportDocument.getUserId())
                                .questionAndAnswer(userReportDocument.getQuestionAndAnswer())
                                .behavioralImprovement(userReportDocument.getBehavioralImprovement())
                                .improvementSuggestion(userReportDocument.getImprovementSuggestion())
                                .overallScore(userReportDocument.getOverallScore())
                                .build();
        }


}
