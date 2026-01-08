package com.interviewmate.notificationservice.consumer;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviewmate.notificationservice.events.OtpEvent;
import com.interviewmate.notificationservice.services.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OTPEventConsumer {

    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;

    private static final String OTP_LISTENING_TOPIC = "otp-events";

    @KafkaListener(topics = OTP_LISTENING_TOPIC, groupId = "notification-service")
    public void consumeOtpEvent(ConsumerRecord<String, String> record){
      log.info("new message arrived");
      String jsonTxt = record.value();
        try {
			OtpEvent otp = objectMapper.readValue(jsonTxt, OtpEvent.class);
      log.info(otp.getEmail(),otp.getOtp());
			notificationService.sendOtpEmail(otp.getEmail(), otp.getOtp());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    }

}
