package com.interviewmate.userservice.eventproducerandconsumer;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviewmate.userservice.dto.NewUserEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewUserRegistrationEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String TOPIC = "user-event";

    @Async
    public void sendMessageToKafka(String userEmail, NewUserEvent userEvent) {
        try {
            String message = objectMapper.writeValueAsString(userEvent);
            // log.info("Preparing message to Kafka: {}", message);

            CompletableFuture<SendResult<String, String>> future =
                    kafkaTemplate.send(TOPIC, userEmail, message);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    // log.info("Message sent to Kafka successfully. Topic: {}, Partition: {}, Offset: {}",
                    //         result.getRecordMetadata().topic(),
                    //         result.getRecordMetadata().partition(),
                    //         result.getRecordMetadata().offset());
                } else {
                    // log.error("Failed to send message to Kafka", ex);
                }
            });

        } catch (JsonProcessingException e) {
            // log.error("Error serializing UserResponse object", e);
        }
    }
}