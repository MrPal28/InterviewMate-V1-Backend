package com.interviewmate.userservice.service.implementation;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviewmate.userservice.dto.OTPRequest;
import com.interviewmate.userservice.service.OTPService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Service
@RequiredArgsConstructor
@Slf4j
public class OtpServiceImpl implements OTPService{

  private final RedisTemplate<String, String> redisTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

     private static final String OTP_CACHE_PREFIX = "otp:";
    private static final long OTP_TTL_MINUTES = 5;

  @Override
  public String generateOtp(OTPRequest request) {
    String email = request.getEmail();

        // Generate 6-digit random OTP
        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        // Store OTP in Redis with TTL
        String key = OTP_CACHE_PREFIX + email;
        redisTemplate.opsForValue().set(key, otp, OTP_TTL_MINUTES, TimeUnit.MINUTES);

        // log.info("Generated OTP {} for email {} (stored in Redis)", otp, email);

        // Publish OTP event to Kafka (for notification-service)
        try {
            Map<String, String> otpEvent = Map.of("email", email, "otp", otp);
            String message = objectMapper.writeValueAsString(otpEvent);
            kafkaTemplate.send("otp-events", email, message);
            // log.info("OTP event sent to Kafka for {}", email);
        } catch (JsonProcessingException e) {
            // log.error("Failed to send OTP event to Kafka", e);
        }

        return otp; 
  }

  @Override
  public boolean verifyOtp(String email, String otp) {
     String key = OTP_CACHE_PREFIX + email;
        String cachedOtp = redisTemplate.opsForValue().get(key);

        if (cachedOtp == null) {
            // log.warn("No OTP found in Redis for {}", email);
            return false;
        }

        if (cachedOtp.equals(otp)) {
            // OTP valid → remove from cache
            redisTemplate.delete(key);
            // log.info("OTP {} verified successfully for {}", otp, email);
            return true;
        } else {
            // log.warn("Invalid OTP {} for {}", otp, email);
            return false;
        }
  }
}
