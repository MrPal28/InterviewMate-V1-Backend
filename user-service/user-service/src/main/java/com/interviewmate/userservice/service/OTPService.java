package com.interviewmate.userservice.service;

import com.interviewmate.userservice.dto.OTPRequest;

public interface OTPService {
  void generateOtp(OTPRequest email);
  void verifyOtp(String email, String otp);
}
