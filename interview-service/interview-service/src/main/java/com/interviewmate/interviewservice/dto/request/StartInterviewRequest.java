package com.interviewmate.interviewservice.dto.request;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StartInterviewRequest {

    private String resumeUrl;

    private boolean specificQuestionRequirement;

    private List<String> subjectOrTopic; // nullable

    @Min(10)
    private int numberOfQuestions;

    @NotBlank
    private String level;
}
