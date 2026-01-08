package com.interviewmate.resumeservice.service;

import java.util.UUID;

public interface ResumeTemplateService {
    /**
     * Generate PDF for a given user resume and template.
     *
     * @param userId the user ID
     * @param templateId the selected template ID
     * @return download URL of the generated PDF
     */
    String generateResumePdf(UUID userId, String templateId);
}
