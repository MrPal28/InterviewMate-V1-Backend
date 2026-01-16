package com.interviewmate.resumeservice.service.implementation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviewmate.resumeservice.dto.AnalysisResponse;
import com.interviewmate.resumeservice.dto.PythonAnalysisRequest;
import com.interviewmate.resumeservice.dto.PythonAnalysisRequestData;
import com.interviewmate.resumeservice.dto.ResumeAnalysisDTO;
import com.interviewmate.resumeservice.entity.Resume;
import com.interviewmate.resumeservice.entity.ResumeFile;
import com.interviewmate.resumeservice.repository.ResumeFileRepository;
import com.interviewmate.resumeservice.repository.ResumeRepository;
import com.interviewmate.resumeservice.service.PythonAnalysisService;
import com.interviewmate.resumeservice.service.ResumeService;
import com.interviewmate.resumeservice.util.FileUploader;
import com.interviewmate.resumeservice.util.ResumeMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeServiceImpl implements ResumeService {

  private final FileUploader fileUploader;
  private final ResumeFileRepository resumeFileRepository;
  private final ResumeRepository resumeRepository;
  private final PythonAnalysisService pythonClient;

  @Override
  public AnalysisResponse analyzeResumeFile(String userid, MultipartFile file, String jobDescription)
      throws IOException {

    MultipartFile myFile = file; // assuming you have added MultipartFile to your request DTO
    if (myFile == null || myFile.isEmpty()) {
      throw new IllegalArgumentException("No file provided");
    }

    FileUploader.UploadResult uploadResult = fileUploader.uploadFile(myFile);

    ResumeFile doc = new ResumeFile();
    doc.setUserId(userid);
    doc.setFilePublicId(uploadResult.publicId());
    doc.setFileUrl(uploadResult.url());
    doc.setJobDescription(jobDescription.isEmpty() ? null : jobDescription);
    doc.setUploadedAt(LocalDateTime.now());
    resumeFileRepository.save(doc);
    log.info(uploadResult.url());
    PythonAnalysisRequest pythonRequest = new PythonAnalysisRequest();
    pythonRequest.setUserid(userid);
    pythonRequest.setUrl(uploadResult.url());
    pythonRequest.setJobDescription(jobDescription);

    log.info(pythonRequest.toString());

    AnalysisResponse analysisResponse = pythonClient.analyzeResume(pythonRequest);

    return analysisResponse;
  }

    @Override
    @Cacheable(value = "resumeAnalysisCache", key = "#resumeId.toString() + ':' + #jobDescription")
    public AnalysisResponse analyzeStoredResume(UUID resumeId, String jobDescription) {
        log.info("Cache miss for resumeId={}, jobDescription={}", resumeId, jobDescription);

        // Fetch resume from DB
        Resume resumeData = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new IllegalArgumentException("Resume Not Found"));

        // Build request to Python service
        PythonAnalysisRequestData requestData = new PythonAnalysisRequestData();
        requestData.setResume(ResumeServiceImpl.toDTO(resumeData));
        requestData.setJobDescription(jobDescription);

        ObjectMapper mapper = new ObjectMapper();
        try {
            log.info("Request to Python service: {}", mapper.writeValueAsString(requestData));
        } catch (JsonProcessingException e) {
            log.error("Error serializing requestData", e);
        }

        // Call Python service
          AnalysisResponse response = pythonClient.analyzeStoredResume(requestData);
           if (response != null) {
                return response;
            }
            return new AnalysisResponse();  
    }

    public static ResumeAnalysisDTO toDTO(Resume entity) {
        if (entity == null) return null;

        return ResumeAnalysisDTO.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .personalInfo(ResumeMapper.toDTO(entity.getPersonalInfo()))
                .summary(entity.getSummary())
                // fully initialize collections to avoid lazy-loading issues
                .experience(entity.getExperience() != null
                        ? entity.getExperience().stream()
                            .map(ResumeMapper::toDTO)
                            .collect(Collectors.toList())
                        : List.of())
                .education(entity.getEducation() != null
                        ? entity.getEducation().stream()
                            .map(ResumeMapper::toDTO)
                            .collect(Collectors.toList())
                        : List.of())
                .skills(entity.getSkills() != null
                        ? entity.getSkills().stream()
                            .map(ResumeMapper::toDTO)
                            .collect(Collectors.toList())
                        : List.of())
                .projects(entity.getProjects() != null
                        ? entity.getProjects().stream()
                            .map(ResumeMapper::toDTO)
                            .collect(Collectors.toList())
                        : List.of())
                .build();
    }
  
}
