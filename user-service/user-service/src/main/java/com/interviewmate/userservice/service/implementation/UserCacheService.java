package com.interviewmate.userservice.service.implementation;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.interviewmate.userservice.dto.CompanyDTO;
import com.interviewmate.userservice.dto.EducationDTO;
import com.interviewmate.userservice.dto.UserResponse;
import com.interviewmate.userservice.exception.UserNotFoundException;
import com.interviewmate.userservice.model.User;
import com.interviewmate.userservice.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserCacheService {

    private final UserRepository userRepo;

    public UserCacheService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Cacheable(value = "userProfiles", key = "#email")
    public UserResponse getUserProfileByEmail(String email) {
        log.info("Fetching from DB for email: {}", email);
        User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
        return mapToUserResponse(user);
    }

     private UserResponse mapToUserResponse(User user) {
    return UserResponse.builder()
        // Common fields
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .phoneNumber(user.getPhoneNumber())
        .role(user.getRole().name())

        // Employee fields
        .yearsOfExperience(user.getYearsOfExperience())
        .skills(user.getSkills())

        // Student fields
        .educations(user.getEducations() != null
            ? user.getEducations().stream()
                .map(edu -> EducationDTO.builder()
                    .collegeName(edu.getCollegeName())
                    .degreeName(edu.getDegreeName())
                    .startYear(edu.getStartYear())
                    .endYear(edu.getEndYear())
                    .build())
                .toList()
            : null)
          .companies(user.getCompanies() != null
            ? user.getCompanies().stream()
                .map(comp -> CompanyDTO.builder()
                    .companyName(comp.getCompanyName())
                    .position(comp.getPosition())
                    .startYear(comp.getStartYear())
                    .endYear(comp.getEndYear())
                    .build())
                .toList()
            : null)
        .build();
  }
}
