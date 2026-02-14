package com.interviewmate.interviewservice.entity;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.interviewmate.interviewservice.dto.response.QuestionAnswerEvaluationDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "user_reports")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReportDocument {

    @Id
    private String sessionId;   // same as interviewId

    private String userId;

    private List<QuestionAnswerEvaluationDto> questionAndAnswer;

    private String behavioralImprovement;

    private String improvementSuggestion;

    private int overallScore;

    private Instant createdAt;
}
