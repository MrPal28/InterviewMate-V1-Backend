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
import com.interviewmate.userservice.exception.InvalidOtpException;
import com.interviewmate.userservice.exception.OtpExpiredException;
import com.interviewmate.userservice.exception.OtpNotificationFailedException;
import com.interviewmate.userservice.exception.RedisUnavailableException;
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
    public void generateOtp(OTPRequest request) {

        String email = request.getEmail();
        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        String key = OTP_CACHE_PREFIX + email;

        try {
            redisTemplate.opsForValue()
                    .set(key, otp, OTP_TTL_MINUTES, TimeUnit.MINUTES);

        } catch (Exception ex) {
            log.error("Redis failure while storing OTP", ex);
            throw new RedisUnavailableException();
        }

        try {
            Map<String, String> otpEvent = Map.of("email", email, "otp", otp);
            String message = objectMapper.writeValueAsString(otpEvent);
            kafkaTemplate.send("otp-events", email, message);

        } catch (JsonProcessingException ex) {
            log.error("OTP serialization failed", ex);
            throw new OtpNotificationFailedException();

        } catch (Exception ex) {
            log.error("Kafka failure while sending OTP", ex);
            throw new OtpNotificationFailedException();
        }
    }


    @Override
    public void verifyOtp(String email, String otp) {

        String key = OTP_CACHE_PREFIX + email;

        String cachedOtp;

        try {
            cachedOtp = redisTemplate.opsForValue().get(key);
        } catch (Exception ex) {
            log.error("Redis failure while verifying OTP", ex);
            throw new RedisUnavailableException();
        }

        if (cachedOtp == null) {
            throw new OtpExpiredException();
        }

        if (!cachedOtp.equals(otp)) {
            throw new InvalidOtpException();
        }

        redisTemplate.delete(key);
    }

}
