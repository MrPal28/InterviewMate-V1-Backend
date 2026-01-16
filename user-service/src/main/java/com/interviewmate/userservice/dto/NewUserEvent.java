package com.interviewmate.userservice.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder@AllArgsConstructor
@NoArgsConstructor
public class NewUserEvent {
  private UUID id;
  private String name;
  private String email;
  private String phoneNumber;
}
