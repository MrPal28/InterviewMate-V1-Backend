package com.interviewmate.resumeservice.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResumeAnalysisDTO {
  private UUID id;
  private UUID userId;
    private PersonalInfoDTO personalInfo;
    private String summary;
    private List<ExperienceResponse> experience;
    private List<EducationResponse> education;
    private List<SkillResponse> skills;
    private List<ProjectResponse> projects;
}
