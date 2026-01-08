package com.interviewmate.resumeservice.service.implementation;


import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import org.springframework.stereotype.Service;

import com.interviewmate.resumeservice.client.PythonAnalysisClient;
import com.interviewmate.resumeservice.dto.AnalysisResponse;
import com.interviewmate.resumeservice.dto.PythonAnalysisRequest;
import com.interviewmate.resumeservice.dto.PythonAnalysisRequestData;
import com.interviewmate.resumeservice.service.PythonAnalysisService;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PythonAnalysisServiceImpl implements PythonAnalysisService {

    private final PythonAnalysisClient client;
    private final CircuitBreakerRegistry cbRegistry;
    private final RetryRegistry retryRegistry;
    private final TimeLimiterRegistry timeLimiterRegistry;
    private final ExecutorService resilienceExecutor; // from config

    private static final String SERVICE_NAME = "pythonService";

    @Override
    public AnalysisResponse analyzeResume(PythonAnalysisRequest request) {
        CircuitBreaker cb = cbRegistry.circuitBreaker(SERVICE_NAME);
        Retry retry = retryRegistry.retry(SERVICE_NAME);
        TimeLimiter tl = timeLimiterRegistry.timeLimiter(SERVICE_NAME);

        Callable<AnalysisResponse> baseCall = () -> client.analyzeResume(request);

        // decorate: Retry -> CircuitBreaker
        Callable<AnalysisResponse> retryable = Retry.decorateCallable(retry, baseCall);
        Callable<AnalysisResponse> protectedCall = CircuitBreaker.decorateCallable(cb, retryable);

        try {
            // TimeLimiter expects a Supplier<CompletableFuture<T>>. We run protectedCall inside the executor.
            return TimeLimiter.decorateFutureSupplier(tl, () ->
                    CompletableFuture.supplyAsync(() -> {
                        try {
                            return protectedCall.call();
                        } catch (Exception ex) {
                            // wrap checked exceptions to runtime for the future
                            throw new RuntimeException(ex);
                        }
                    }, resilienceExecutor)
            ).call(); // call() blocks until future completes or times out
        } catch (Exception ex) {
            // log root cause details
            log.error("analyzeResume failed, cbState={}, cause={}", cb.getState(), ex.toString());
            return fallbackAnalyzeResume(request, ex);
        }
    }

    @Override
    public AnalysisResponse analyzeStoredResume(PythonAnalysisRequestData requestData) {
        CircuitBreaker cb = cbRegistry.circuitBreaker(SERVICE_NAME);
        Retry retry = retryRegistry.retry(SERVICE_NAME);
        TimeLimiter tl = timeLimiterRegistry.timeLimiter(SERVICE_NAME);

        Callable<AnalysisResponse> baseCall = () -> client.analyzeStoredResume(requestData);
        Callable<AnalysisResponse> retryable = Retry.decorateCallable(retry, baseCall);
        Callable<AnalysisResponse> protectedCall = CircuitBreaker.decorateCallable(cb, retryable);

        try {
            return TimeLimiter.decorateFutureSupplier(tl, () ->
                    CompletableFuture.supplyAsync(() -> {
                        try {
                            return protectedCall.call();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }, resilienceExecutor)
            ).call();
        } catch (Exception ex) {
            log.error("analyzeStoredResume failed, cbState={}, cause={}", cb.getState(), ex.toString());
            return fallbackAnalyzeStoredResume(requestData, ex);
        }
    }

    private AnalysisResponse fallbackAnalyzeResume(PythonAnalysisRequest request, Throwable ex) {
        // Build a helpful fallback — minimal but informative
        log.warn("Fallback for analyzeResume invoked due to: {}", ex.toString());
        return AnalysisResponse.builder()
                .build();
    }

    private AnalysisResponse fallbackAnalyzeStoredResume(PythonAnalysisRequestData requestData, Throwable ex) {
        log.warn("Fallback for analyzeStoredResume invoked due to: {}", ex.toString());
        return AnalysisResponse.builder()
                .build();
    }
}
