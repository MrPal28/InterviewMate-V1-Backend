package com.interviewmate.codingservice.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.interviewmate.codingservice.constants.SubmissionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "submissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Submission {

    @Id
    private String id;

    private String problemId;
    private String userId;

    private String language;
    private String sourceCode;
    private SubmissionStatus status;
    private String verdict;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
