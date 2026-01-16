package com.interviewmate.resumeservice.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.interviewmate.resumeservice.dto.AnalysisResponse;

public interface ResumeService {
    
    // === Resume Analysis ===

    /**
     * Analyze a resume (uploaded file flow).
     * Mirrors: POST /resume/analyze
     */
    AnalysisResponse analyzeResumeFile(String userid , MultipartFile file , String jobDescription) throws IOException;

    /**
     * Analyze an already-saved resume (inbuilt data flow).
     * Sends stored resume URL + JD to Python.
     */
    AnalysisResponse analyzeStoredResume(UUID resumeId, String jobDescription);


    // === Templates & Export ===

    /**
     * Fetch available resume templates.
     * Mirrors: GET /resume/templates
     */
    // List<ResumeTemplateResponse> getResumeTemplates();

    /**
     * Export resume to a chosen template and generate PDF.
     * Mirrors: POST /resume/export
     */
    // byte[] exportResume(Long userId, String templateId);
}
