package com.interviewmate.codingservice.config;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class SubmissionStreamManager {

    // Only ONE emitter per submission (because each POST opens only one)
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter createStream(String submissionId) {
        SseEmitter emitter = new SseEmitter(0L); // no timeout
        emitters.put(submissionId, emitter);
        return emitter;
    }

      public SseEmitter createRunStream(String executionId) {
        SseEmitter emitter = new SseEmitter(30_000L);

        emitters.put(executionId, emitter);

        emitter.onCompletion(() -> cleanup(executionId));
        emitter.onTimeout(() -> cleanup(executionId));
        emitter.onError(e -> cleanup(executionId));

        log.info("SSE stream created for executionId={}", executionId);
        return emitter;
    }

    public void send(String submissionId, String event, Object data) {
        SseEmitter emitter = emitters.get(submissionId);
        if (emitter == null) return;

        try {
            emitter.send(SseEmitter.event()
                    .name(event)
                    .data(data));
        } catch (Exception e) {
            emitter.complete();
            emitters.remove(submissionId);
        }
    }

    public void complete(String submissionId) {
        SseEmitter emitter = emitters.remove(submissionId);
        if (emitter != null) {
            emitter.complete();
        }
        log.info("Closed SSE stream for submission: {}", submissionId);
    }

    private void cleanup(String executionId) {
        emitters.remove(executionId);
        log.warn("SSE stream cleaned for executionId={}", executionId);
    }
}
