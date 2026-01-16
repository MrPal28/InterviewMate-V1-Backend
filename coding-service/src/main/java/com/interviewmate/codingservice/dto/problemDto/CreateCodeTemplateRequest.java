package com.interviewmate.codingservice.dto.problemDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCodeTemplateRequest {
  @NotBlank
  private String language;
  @NotBlank
  private String template;
}
