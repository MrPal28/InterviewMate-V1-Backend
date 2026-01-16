package com.interviewmate.notificationservice.events;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserEvent {
  private UUID id;
  private String name;
  private String email;
  private String phoneNumber;
}