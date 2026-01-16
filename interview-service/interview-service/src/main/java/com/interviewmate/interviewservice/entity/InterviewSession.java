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
    private List<String> questions;
    private int totalQuestions;
    private int remainingQuestions;
    private int currentQuestion;
    private InterviewStatus status;
    private boolean secondSlotFetched;
    private Instant createdAt;
}
