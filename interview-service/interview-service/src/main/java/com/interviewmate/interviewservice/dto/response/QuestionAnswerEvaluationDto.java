package com.interviewmate.interviewservice.dto.response;


import lombok.Data;

@Data
public class QuestionAnswerEvaluationDto {

    private int questionNo;

    private String question;

    private String answer;                // User's spoken answer (STT output)

    private String actualQuestionAnswer;  // Ideal / expected answer
}
