package com.interviewmate.interviewservice.client.python;


import com.interviewmate.interviewservice.dto.request.PythonInitInterviewRequest;
import com.interviewmate.interviewservice.dto.response.PythonInitInterviewResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PythonInterviewClient {

    private final WebClient pythonInterviewWebClient;

    private static final String CB_NAME = "pythonInterviewService";

    @CircuitBreaker(name = CB_NAME)
    @Retry(name = CB_NAME)
    @TimeLimiter(name = CB_NAME)
    public Mono<PythonInitInterviewResponse> getFirstSlotQuestions(
            PythonInitInterviewRequest request) {

        return pythonInterviewWebClient.post()
                .uri("/interviewservice/api/v1/initializeinterview/getfirstslotquestion")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PythonInitInterviewResponse.class);
    }

    @CircuitBreaker(name = CB_NAME)
    @Retry(name = CB_NAME)
    @TimeLimiter(name = CB_NAME)
    public Mono<PythonInitInterviewResponse> getSecondSlotQuestions(
            Object request) {

        return pythonInterviewWebClient.post()
                .uri("/interviewservice/api/v1/initializeinterview/getsecondslotquestion")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PythonInitInterviewResponse.class);
    }
}
