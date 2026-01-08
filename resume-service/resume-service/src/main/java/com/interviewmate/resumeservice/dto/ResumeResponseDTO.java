package com.interviewmate.resumeservice.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeResponseDTO implements Serializable{
    private UUID id;
    private PersonalInfoDTO personalInfo;
    private String summary;
    private List<ExperienceResponse> experience;
    private List<EducationResponse> education;
    private List<SkillResponse> skills;
    private List<ProjectResponse> projects;
}