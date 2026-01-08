package com.interviewmate.resumeservice.config;


import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;

@Configuration
public class ResilienceConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig defaultCb = CircuitBreakerConfig.custom()
                .failureRateThreshold(60)                     // allow up to 60% failures
                .slidingWindowSize(50)                        // larger window for better stats
                .minimumNumberOfCalls(20)                     // wait for 20 calls before evaluating
                .waitDurationInOpenState(Duration.ofSeconds(30)) // breaker resets faster
                .permittedNumberOfCallsInHalfOpenState(5)
                .recordExceptions(java.io.IOException.class)  // treat IO errors as failures
                .ignoreExceptions(java.util.concurrent.TimeoutException.class) // don’t trip on slow responses
                .build();
        return CircuitBreakerRegistry.of(defaultCb);
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig defaultRetry = RetryConfig.custom()
                .maxAttempts(1)  // no retry for long-running tasks
                .waitDuration(Duration.ofMillis(200)) // small delay (kept for formality)
                .retryExceptions(java.io.IOException.class) // only retry on true IO exceptions
                .build();
        return RetryRegistry.of(defaultRetry);
    }

    @Bean
    public TimeLimiterRegistry timeLimiterRegistry() {
        TimeLimiterConfig defaultTl = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(40)) // cushion above 25–30s
                .cancelRunningFuture(true)               // cancel if truly stuck
                .build();
        return TimeLimiterRegistry.of(defaultTl);
    }

    /**
     * Executor for async TimeLimiter + circuit breaker tasks.
     * Cached thread pool grows/shrinks dynamically with load.
     */
    @Bean(destroyMethod = "shutdown")
    public ExecutorService resilienceExecutor() {
        return Executors.newCachedThreadPool();
    }
}