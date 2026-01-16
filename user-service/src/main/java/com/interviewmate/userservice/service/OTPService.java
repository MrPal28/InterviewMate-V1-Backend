package com.interviewmate.userservice.service;

import com.interviewmate.userservice.dto.OTPRequest;

public interface OTPService {
  String generateOtp(OTPRequest email);
  boolean verifyOtp(String email, String otp);
}
