package com.interviewmate.notificationservice.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviewmate.notificationservice.events.NewUserEvent;
import com.interviewmate.notificationservice.services.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewUserEventConsumer {
  private final ObjectMapper objectMapper;
    private final NotificationService notificationService;

    private static final String USER_LISTENING_TOPIC = "user-event";


    @KafkaListener(topics = USER_LISTENING_TOPIC, groupId = "notification-service")
    public void consumeOtpEvent(ConsumerRecord<String, String> record){
      log.info("new message arrived");
      String jsonTxt = record.value();
        try {
			NewUserEvent user = objectMapper.readValue(jsonTxt, NewUserEvent.class);
      log.info(user.getEmail(),user.getName());
			notificationService.sendWelcomeEmail(user.getEmail(), user.getName());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    }
}
