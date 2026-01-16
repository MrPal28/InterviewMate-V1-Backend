package com.interviewmate.resumeservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResumeRequest {
  private PersonalInfoDTO personalInfo;
  private String summary;
  private List<EducationDTO> education;
  private List<ExperienceDTO> experiences;
  private List<SkillDTO> skills;
  private List<ProjectDTO> projects;
}
