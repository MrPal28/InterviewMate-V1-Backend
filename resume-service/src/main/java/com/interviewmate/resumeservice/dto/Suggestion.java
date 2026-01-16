package com.interviewmate.resumeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Suggestion {
  private String section;
  private String recommendation;
  private String priority;
}
