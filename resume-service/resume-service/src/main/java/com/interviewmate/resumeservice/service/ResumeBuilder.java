package com.interviewmate.resumeservice.service;

import java.util.UUID;

import com.interviewmate.resumeservice.dto.ResumeRequest;
import com.interviewmate.resumeservice.dto.ResumeResponseDTO;


public interface ResumeBuilder {
    ResumeResponseDTO saveOrUpdateResume(UUID userId, ResumeRequest request);
    ResumeResponseDTO getResume(UUID userId);
}
