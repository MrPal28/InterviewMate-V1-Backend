package com.interviewmate.userservice.service;

import com.interviewmate.userservice.dto.LoginRequestDTO;
import com.interviewmate.userservice.dto.LoginResponse;
import com.interviewmate.userservice.dto.RegisterRequestDTO;
import com.interviewmate.userservice.dto.UserResponse;

public interface AuthService {
  UserResponse register(RegisterRequestDTO request);
  LoginResponse login(LoginRequestDTO request);
  boolean resetPassword(String email, String otp, String newPassword);
  
} 