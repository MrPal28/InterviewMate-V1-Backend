package com.interviewmate.interviewservice.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Data
public class SubmitAnswerRequest {
    @NotBlank
    private String sessionId;     
    @Positive
    private int questionNo;
    @NotBlank
    private String question;
    @NotBlank
    private String videoUrl;
    @Positive
    private int totalNumberOfQuestion;

}
