package com.interviewmate.codingservice.dto.problemDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTestCaseRequest {
    @NotBlank
    private String stdin;     // full stdin block
    @NotBlank
    private String stdout;
      
    private boolean hidden;
}   