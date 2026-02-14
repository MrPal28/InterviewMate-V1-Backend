package com.interviewmate.interviewservice.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.interviewmate.interviewservice.constants.InterviewStatus;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("INTERVIEW_SESSION")
public class InterviewSession implements Serializable {


    @Id
    private String sessionId;          
    private String userId;

    
    private String resumeUrl;
    private boolean specificQuestionRequirement;
    private List<String> subjectOrTopic;
    private int totalQuestions;
    private String level;

    
    private int currentQuestion;       
    private int remainingQuestions;

    private boolean slotOneFetched;
    private boolean slotTwoFetched;

    private InterviewStatus status;    

    
    private List<String> questions;    

    
    private Instant createdAt;
    private Instant updatedAt;
}
