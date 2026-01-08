package com.interviewmate.notificationservice.services;

public interface NotificationService {

  void sendWelcomeEmail(String email, String name);
  void sendOtpEmail(String email, String otp);
}
