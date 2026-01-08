package com.interviewmate.userservice.dto;

import lombok.Data;

@Data
public class PasswordResetRequest {
  private String email;
  private String otp;
  private String newPassword;
}
