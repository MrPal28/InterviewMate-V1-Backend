package com.interviewmate.codingservice.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSubmissionRequest {

    @NotBlank
    private String problemId;
    @NotBlank
    private String languageId;
    @NotBlank
    private String sourceCode;
}
