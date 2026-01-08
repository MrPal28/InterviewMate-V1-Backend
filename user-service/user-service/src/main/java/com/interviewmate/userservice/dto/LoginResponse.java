package com.interviewmate.userservice.dto;

import java.util.UUID;

import com.interviewmate.userservice.constants.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
  private UUID id;
  private String token;
  private String email;
  private Role role;
}
