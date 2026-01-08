package com.interviewmate.interviewservice.orchestration;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.interviewmate.interviewservice.client.python.PythonInterviewClient;
import com.interviewmate.interviewservice.constants.InterviewStatus;
import com.interviewmate.interviewservice.dto.QuestionDto;
import com.interviewmate.interviewservice.dto.request.PythonInitInterviewRequest;
import com.interviewmate.interviewservice.dto.request.PythonSecondSlotRequest;
import com.interviewmate.interviewservice.dto.request.SecondSlotRequest;
import com.interviewmate.interviewservice.dto.request.StartInterviewRequest;
import com.interviewmate.interviewservice.dto.response.StartInterviewResponse;
import com.interviewmate.interviewservice.entity.InterviewSession;
import com.interviewmate.interviewservice.repository.InterviewSessionRepository;

import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class InterviewOrchestrator {

    private final PythonInterviewClient pythonInterviewClient;
    private final InterviewSessionRepository sessionRepository;

    public Mono<StartInterviewResponse> startInterview(StartInterviewRequest request) {

        PythonInitInterviewRequest pythonRequest =
                PythonInitInterviewRequest.builder()
                        .userid(request.getUserId())
                        .resumeurl(request.getResumeUrl())
                        .specificquestionrequirement(request.isSpecificQuestionRequirement())
                        .subjectortopic(request.getSubjectOrTopic())
                        .numberofquestions(request.getNumberOfQuestions())
                        .level(request.getLevel())
                        .build();

        return pythonInterviewClient.getFirstSlotQuestions(pythonRequest)
                .flatMap(pythonResponse -> {

                    InterviewSession session = InterviewSession.builder()
                            .sessionId(pythonResponse.getSessionid())
                            .userId(pythonResponse.getUserid())
                            .questions(pythonResponse.getSlotonequestions())
                            .totalQuestions(pythonResponse.getNumberofquestions())
                            .remainingQuestions(pythonResponse.getRemanning())
                            .currentQuestion(1)
                            .status(InterviewStatus.IN_PROGRESS)
                            .createdAt(Instant.now())
                            .build();

                    return sessionRepository.save(session)
                            .map(savedSession -> StartInterviewResponse.builder()
                                    .sessionId(savedSession.getSessionId())
                                    .questions(mapQuestions(savedSession.getQuestions()))
                                    .remaining(savedSession.getRemainingQuestions())
                                    .totalQuestions(savedSession.getTotalQuestions())
                                    .build()
                            );
                });
    }

    private List<QuestionDto> mapQuestions(List<String> questions) {
        return IntStream.range(0, questions.size())
                .mapToObj(i -> new QuestionDto(i + 1, questions.get(i)))
                .toList();
    }

    public Mono<StartInterviewResponse> fetchSecondSlot(SecondSlotRequest request) {

    return sessionRepository.findById(request.getSessionId())
        .switchIfEmpty(Mono.error(new IllegalStateException("Session not found")))
        .flatMap(session -> {

            if (session.isSecondSlotFetched()) {
                return Mono.just(buildResponse(session));
            }

            PythonSecondSlotRequest pythonRequest =
                    PythonSecondSlotRequest.builder()
                            .userid(request.getUserId())
                            .sessionid(request.getSessionId())
                            .remanning(request.getRemanning())
                            .level(request.getLevel())
                            .build();

            return pythonInterviewClient.getSecondSlotQuestions(pythonRequest)
                    .flatMap(pythonResponse -> {

                        session.getQuestions()
                                .addAll(pythonResponse.getSlottwoquestions());

                        session.setRemainingQuestions(0);
                        session.setSecondSlotFetched(true);

                        return sessionRepository.save(session)
                                .map(this::buildResponse);
                    });
        });
    }

    private StartInterviewResponse buildResponse(InterviewSession session) {

    List<QuestionDto> questionDtos =
            IntStream.range(0, session.getQuestions().size())
                    .mapToObj(i -> new QuestionDto(i + 1, session.getQuestions().get(i)))
                    .toList();

    return StartInterviewResponse.builder()
            .sessionId(session.getSessionId())
            .questions(questionDtos)
            .remaining(session.getRemainingQuestions())
            .totalQuestions(session.getTotalQuestions())
            .build();
    }

}

