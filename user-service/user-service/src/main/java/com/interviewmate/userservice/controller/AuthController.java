package com.interviewmate.userservice.controller;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interviewmate.userservice.dto.ApiResponse;
import com.interviewmate.userservice.dto.LoginRequestDTO;
import com.interviewmate.userservice.dto.LoginResponse;
import com.interviewmate.userservice.dto.OTPRequest;
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
      otpService.generateOtp(request);
      return ResponseEntity.ok("OTP has been sent to your email");
  }

  @PostMapping("/verify-otp")
  public ResponseEntity<ApiResponse<Void>> verifyOtp(
      @RequestParam String email,
      @RequestParam String otp) {

    otpService.verifyOtp(email, otp);

    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .success(true)
            .message("OTP verified successfully")
            .timestamp(Instant.now())
            .build());
  }


  @PostMapping("/forgot-password")
  public ResponseEntity<String> forgotPassword(@Valid @RequestBody OTPRequest request) {
    otpService.generateOtp(request);
    return ResponseEntity.ok("OTP has been sent to your email");
  }

  @PostMapping("/reset-password")
  public ResponseEntity<ApiResponse<Void>> resetPassword(
      @RequestParam String email,
      @RequestParam String otp,
      @RequestParam String newPassword) {

    authService.resetPassword(email, otp, newPassword);

    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .success(true)
            .message("Password reset successfully")
            .timestamp(Instant.now())
            .build());
  }


}
