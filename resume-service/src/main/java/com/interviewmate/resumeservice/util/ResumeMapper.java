package com.interviewmate.resumeservice.util;

import com.interviewmate.resumeservice.dto.EducationDTO;
import com.interviewmate.resumeservice.dto.EducationResponse;
import com.interviewmate.resumeservice.dto.ExperienceDTO;
import com.interviewmate.resumeservice.dto.ExperienceResponse;
import com.interviewmate.resumeservice.dto.PersonalInfoDTO;
import com.interviewmate.resumeservice.dto.ProjectDTO;
import com.interviewmate.resumeservice.dto.ProjectResponse;
import com.interviewmate.resumeservice.dto.ResumeRequest;
import com.interviewmate.resumeservice.dto.ResumeResponseDTO;
import com.interviewmate.resumeservice.dto.SkillDTO;
import com.interviewmate.resumeservice.dto.SkillResponse;
import com.interviewmate.resumeservice.entity.Education;
import com.interviewmate.resumeservice.entity.Experience;
import com.interviewmate.resumeservice.entity.PersonalInfo;
import com.interviewmate.resumeservice.entity.Project;
import com.interviewmate.resumeservice.entity.Resume;
import com.interviewmate.resumeservice.entity.Skill;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResumeMapper {

    // ==================== Resume ====================
    public static ResumeResponseDTO toDTO(Resume entity) {
        if (entity == null) return null;

        return ResumeResponseDTO.builder()
                .id(entity.getId())
                .personalInfo(toDTO(entity.getPersonalInfo()))
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

    public static Resume toEntity(ResumeRequest dto) {
        if (dto == null) return null;

        Resume resume = Resume.builder()
                .personalInfo(toEntity(dto.getPersonalInfo()))
                .summary(dto.getSummary())
                .build();

        if (dto.getExperiences() != null) {
            resume.setExperience(dto.getExperiences().stream()
                    .map(e -> {
                        Experience exp = toEntity(e);
                        exp.setResume(resume); // maintain parent reference
                        return exp;
                    })
                    .collect(Collectors.toList()));
        }

        if (dto.getEducation() != null) {
            resume.setEducation(dto.getEducation().stream()
                    .map(e -> {
                        Education edu = toEntity(e);
                        edu.setResume(resume);
                        return edu;
                    })
                    .collect(Collectors.toList()));
        }

        if (dto.getSkills() != null) {
            resume.setSkills(dto.getSkills().stream()
                    .map(s -> {
                        Skill skill = toEntity(s);
                        skill.setResume(resume);
                        return skill;
                    })
                    .collect(Collectors.toList()));
        }

        if (dto.getProjects() != null) {
            resume.setProjects(dto.getProjects().stream()
                    .map(p -> {
                        Project project = toEntity(p);
                        project.setResume(resume);
                        return project;
                    })
                    .collect(Collectors.toList()));
        }

        return resume;
    }



    // ==================== PersonalInfo ====================
    public static PersonalInfoDTO toDTO(PersonalInfo entity) {
        if (entity == null) return null;
        return PersonalInfoDTO.builder()
                .fullName(entity.getFullName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .location(entity.getLocation())
                .linkedIn(entity.getLinkedIn())
                .portfolio(entity.getPortfolio())
                .build();
    }

    public static PersonalInfo toEntity(PersonalInfoDTO dto) {
        if (dto == null) return null;
        return PersonalInfo.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .location(dto.getLocation())
                .linkedIn(dto.getLinkedIn())
                .portfolio(dto.getPortfolio())
                .build();
    }

    // ==================== Experience ====================
    public static ExperienceResponse toDTO(Experience entity) {
        if (entity == null) return null;
        return ExperienceResponse.builder()
                .id(entity.getId())
                .company(entity.getCompany())
                .position(entity.getPosition())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .current(entity.isCurrent())
                .description(entity.getDescription())
                .build();
    }

    public static Experience toEntity(ExperienceDTO dto) {
        if (dto == null) return null;
        return Experience.builder()
                .company(dto.getCompany())
                .position(dto.getPosition())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .current(dto.isCurrent())
                .description(dto.getDescription())
                .build();
    }

    // ==================== Education ====================
    public static EducationResponse toDTO(Education entity) {
        if (entity == null) return null;
        return EducationResponse.builder()
                .id(entity.getId())
                .institution(entity.getInstitution())
                .degree(entity.getDegree())
                .field(entity.getField())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .gpa(entity.getGpa())
                .build();
    }

    public static Education toEntity(EducationDTO dto) {
        if (dto == null) return null;
        return Education.builder()
                .institution(dto.getInstitution())
                .degree(dto.getDegree())
                .field(dto.getField())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .gpa(dto.getGpa())
                .build();
    }

    // ==================== Skill ====================
    public static SkillResponse toDTO(Skill entity) {
        if (entity == null) return null;
        // force items initialization to avoid lazy-loading
        List<String> items = entity.getItems() != null ? new ArrayList<>(entity.getItems()) : List.of();
        return SkillResponse.builder()
                .id(entity.getId())
                .category(entity.getCategory())
                .items(items)
                .build();
    }

    public static Skill toEntity(SkillDTO dto) {
        if (dto == null) return null;
        return Skill.builder()
                .category(dto.getCategory())
                .items(dto.getItems() != null ? new ArrayList<>(dto.getItems()) : List.of())
                .build();
    }

    // ==================== Project ====================
    public static ProjectResponse toDTO(Project entity) {
        if (entity == null) return null;
        List<String> technologies = entity.getTechnologies() != null ? new ArrayList<>(entity.getTechnologies()) : List.of();
        return ProjectResponse.builder()
                .id(entity.getProjectId())
                .name(entity.getName())
                .description(entity.getDescription())
                .link(entity.getLink())
                .technologies(technologies)
                .build();
    }

    public static Project toEntity(ProjectDTO dto) {
        if (dto == null) return null;
        return Project.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .link(dto.getLink())
                .technologies(dto.getTechnologies() != null ? new ArrayList<>(dto.getTechnologies()) : List.of())
                .build();
    }
}
