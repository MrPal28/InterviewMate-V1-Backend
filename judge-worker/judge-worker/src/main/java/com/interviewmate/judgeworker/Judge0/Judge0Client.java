package com.interviewmate.judgeworker.Judge0;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class Judge0Client {
    private final WebClient webClient;

    public Judge0Client(WebClient judge0WebClient) { // configure WebClient bean separately
        this.webClient = judge0WebClient;
    }

    public Mono<String> submit(Map<String, Object> body) {
        // POST /submissions?wait=false typical Judge0 endpoint, response contains
        // token/id
        return webClient.post()
                .uri("/submissions?wait=false")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class) // judge0 returns JSON with token/id field e.g. token or 'token'/'token' key
                                       // depends on version
                .map(map -> {
                    Object token = map.get("token"); // adjust to actual field e.g., "token" or "token" field
                    return token == null ? null : token.toString();
                });
    }

    public Mono<Map<String, Object>> getSubmissionResult(String token) {
        return webClient.get()
                .uri("/submissions/{token}", token)
                .retrieve()
                .bodyToMono(new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {
                });
    }
}
