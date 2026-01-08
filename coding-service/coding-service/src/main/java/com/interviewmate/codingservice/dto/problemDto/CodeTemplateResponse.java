package com.interviewmate.codingservice.dto.problemDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeTemplateResponse {
  private String languageId;
  private String template;
}
