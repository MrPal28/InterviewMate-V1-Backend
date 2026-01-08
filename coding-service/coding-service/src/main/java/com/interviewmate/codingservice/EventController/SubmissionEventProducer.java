package com.interviewmate.codingservice.EventController;

import java.util.Map;
import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.interviewmate.codingservice.events.SubmissionCreatedEventDto;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SubmissionEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topic = "submissions.created";

    public SubmissionEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreatedEvent(SubmissionCreatedEventDto submissionCreatedEventDto) {
        String traceId = UUID.randomUUID().toString();
        Map<String, Object> payload = Map.of(
            "submissionId", submissionCreatedEventDto.getSubmissionId(),
            "userId", submissionCreatedEventDto.getUserId(),
            "problemId", submissionCreatedEventDto.getProblemId(),
            "languageId", submissionCreatedEventDto.getLanguageId(),
            "sourceCode", submissionCreatedEventDto.getSourceCode(),
            "testCases", submissionCreatedEventDto.getTestCases(),
            "createdAt", java.time.Instant.now().toString(),
            "traceId", traceId
        );
        log.info("Publishing submission created event: {}", payload);
        kafkaTemplate.send(topic, submissionCreatedEventDto.getSubmissionId(), payload);
    }
}