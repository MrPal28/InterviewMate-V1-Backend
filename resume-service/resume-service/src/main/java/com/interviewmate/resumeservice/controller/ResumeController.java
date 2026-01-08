package com.interviewmate.resumeservice.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.interviewmate.resumeservice.dto.AnalysisResponse;
import com.interviewmate.resumeservice.dto.ResumeAnalysisRequest;
import com.interviewmate.resumeservice.dto.ResumeRequest;
import com.interviewmate.resumeservice.dto.ResumeResponseDTO;
import com.interviewmate.resumeservice.service.ResumeBuilder;
import com.interviewmate.resumeservice.service.ResumeService;
import com.interviewmate.resumeservice.service.ResumeTemplateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/resume")
public class ResumeController {
  private final ResumeService resumeService;
  private final ResumeBuilder resumeBuilder;
  private final ResumeTemplateService resumeTemplateService;

  @PostMapping("/analyze-file")
  public ResponseEntity<AnalysisResponse> analyzeResume(
      @RequestParam("file") MultipartFile file,
      @RequestParam("jobDescription") String jobDescription,
      @RequestHeader("x-user-id") String userId //  extract UUID from header
  ) throws IOException {

    AnalysisResponse response = resumeService.analyzeResumeFile(
        userId, file, jobDescription);
    System.out.println("userid"+userId);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/analyze-by-id")
public ResponseEntity<AnalysisResponse> analyzeResumeById(
        @RequestBody ResumeAnalysisRequest request,
        @RequestHeader("x-user-id") String userId
) {
    UUID resumeUUID = UUID.fromString(request.getResumeId());

    // Access jobDescription through the request DTO
    AnalysisResponse response = resumeService.analyzeStoredResume(
            resumeUUID,
            request.getJobDescription()
    );

    return ResponseEntity.ok(response);
}


  @PostMapping("/save")
  public ResponseEntity<ResumeResponseDTO> saveResume(
      @RequestHeader("x-user-id") String userId,
      @RequestBody ResumeRequest request
  ) {
      UUID userUuid = UUID.fromString(userId);
      ResumeResponseDTO response = resumeBuilder.saveOrUpdateResume(userUuid, request);
      return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<ResumeResponseDTO> getResume(
      @RequestHeader("x-user-id") String userId
  ) {
      UUID userUuid = UUID.fromString(userId);
      ResumeResponseDTO response = resumeBuilder.getResume(userUuid);
      return ResponseEntity.ok(response);
  }

  @PostMapping("/download")
    public ResponseEntity<String> downloadResume(
            @RequestBody String templateId,
            @RequestHeader("x-user-id") String userId) {

        String downloadUrl = resumeTemplateService.generateResumePdf(
                UUID.fromString(userId),
                templateId
        );

        return ResponseEntity.ok(downloadUrl);
    }

}
