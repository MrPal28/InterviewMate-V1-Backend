package com.interviewmate.interviewservice.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReportDto {


    private String userId;

    private String sessionId;   

    private List<QuestionAnswerEvaluationDto> questionAndAnswer;


    private String behavioralImprovement;

 
    private String improvementSuggestion;

    private int overallScore;
}
