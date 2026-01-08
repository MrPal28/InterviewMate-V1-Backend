package com.interviewmate.notificationservice.services.implementations;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.interviewmate.notificationservice.services.NotificationService;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService{

  private final JavaMailSender mailSender;
  @Override
  public void sendWelcomeEmail(String email, String name) {
     try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("Welcome to InterviewMate!");

            try (var inputStream = Objects.requireNonNull(NotificationServiceImpl.class.getResourceAsStream("/templates/welcome-mail.html"))) {
                String htmlContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                htmlContent = htmlContent.replace("{{name}}", name);
                helper.setText(htmlContent, true);
            }

            mailSender.send(message);
            log.info("Email sent successfully to: {}", email);
        } catch (Exception e) {
            log.error("Error sending mail to {}: {}", email, e.getMessage(), e);
            throw new RuntimeException("Failed to send email", e);
        }
  }

  @Override
  public void sendOtpEmail(String email, String otp) {
    try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("One Time OTP Password");

            try (var inputStream = Objects.requireNonNull(NotificationServiceImpl.class.getResourceAsStream("/templates/otptempalte.html"))) {
                String htmlContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                htmlContent = htmlContent.replace("{{otp}}", otp);
                helper.setText(htmlContent, true);
            }

            mailSender.send(message);
            log.info("Email sent successfully to: {}", email);
        } catch (Exception e) {
            log.error("Error sending mail to {}: {}", email, e.getMessage(), e);
            throw new RuntimeException("Failed to send email", e);
        }
  }
  
}
