package com.interviewmate.resumeservice.dto;

import lombok.Data;

@Data
public class ResumeAnalysisRequest {
    private String resumeId;
    private String jobDescription;
}
