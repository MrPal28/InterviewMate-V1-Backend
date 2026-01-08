package com.interviewmate.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interviewmate.userservice.dto.LoginRequestDTO;
import com.interviewmate.userservice.dto.LoginResponse;
import com.interviewmate.userservice.dto.OTPRequest;
import com.interviewmate.userservice.dto.OTPVerificationRequest;
import com.interviewmate.userservice.dto.PasswordResetRequest;
import com.interviewmate.userservice.dto.RegisterRequestDTO;
import com.interviewmate.userservice.dto.UserResponse;
import com.interviewmate.userservice.service.AuthService;
import com.interviewmate.userservice.service.OTPService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final OTPService otpService;


   @PostMapping("/register")
  public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequestDTO request) {
    UserResponse response = authService.register(request);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequestDTO request) {
    LoginResponse response = authService.login(request);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/getotp")
  public ResponseEntity<String> getOtp(@Valid @RequestBody OTPRequest request) {
      String otp = otpService.generateOtp(request);
      return ResponseEntity.ok(otp);
  }

  @PostMapping("/verifyotp")
  public ResponseEntity<Boolean> verifyOtp(@Valid @RequestBody OTPVerificationRequest request) {
      boolean response = otpService.verifyOtp(request.getEmail(), request.getOtp());
      return ResponseEntity.ok(response);
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<String> forgotPassword(@Valid @RequestBody OTPRequest request) {
    String otp = otpService.generateOtp(request);
    return ResponseEntity.ok("OTP has been sent to your email");
  }

  @PostMapping("/reset-password")
  public ResponseEntity<Boolean> resetPassword(@Valid @RequestBody PasswordResetRequest request) {
    boolean result = authService.resetPassword(request.getEmail(), request.getOtp(), request.getNewPassword());
    return ResponseEntity.ok(result);
  }

}
