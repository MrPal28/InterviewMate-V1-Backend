package com.interviewmate.resumeservice.service.implementation;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.interviewmate.resumeservice.entity.Resume;
import com.interviewmate.resumeservice.repository.ResumeRepository;
import com.interviewmate.resumeservice.service.ResumeTemplateService;
import com.interviewmate.resumeservice.util.FileUploader;
import com.interviewmate.resumeservice.util.PdfGenerator;
import com.interviewmate.resumeservice.util.TemplateRenderer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeTemplateServiceImpl implements ResumeTemplateService {

    private final ResumeRepository resumeRepository;
    private final TemplateRenderer templateRenderer; // Thymeleaf utility class
    private final FileUploader fileUploader;

    @Override
    public String generateResumePdf(UUID userId, String templateId) {
        Resume resume = resumeRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Resume not found for user: " + userId));

        String htmlContent = templateRenderer.renderTemplate(templateId, Map.of("resumeData", resume));
        byte[] pdfBytes = PdfGenerator.convertHtmlToPdf(htmlContent);

        // Sanitize templateId for Cloudinary
        String safeTemplateId = templateId.replaceAll("[^a-zA-Z0-9_-]", "");
        String safeFileName = "resumes/resume-" + userId + "-" + safeTemplateId + ".pdf";

        String downloadUrl = fileUploader.uploadPdf(pdfBytes, safeFileName);
        log.info("Resume PDF generated and uploaded: {}", downloadUrl);

        return downloadUrl;
    }

}
