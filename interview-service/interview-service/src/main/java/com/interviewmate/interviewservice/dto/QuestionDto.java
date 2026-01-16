package com.interviewmate.interviewservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionDto {
    private int questionNo;
    private String question;
}
