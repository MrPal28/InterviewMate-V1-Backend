package com.interviewmate.codingservice.dto;

import java.time.LocalDateTime;

import com.interviewmate.codingservice.constants.SubmissionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionResponse {

    private String id;
    private String problemId;
    private String userId;
    private String language;
    private String sourceCode;
    private String stdout;
    private SubmissionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
