package com.interviewmate.interviewservice.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StartInterviewResponse {
    private String userId;
    private String sessionId;
    private List<QuestionDto> questions;
    private int remaining;
    private int totalQuestions;
}
