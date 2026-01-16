package com.interviewmate.userservice.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interviewmate.userservice.dto.CompanyDTO;
import com.interviewmate.userservice.dto.EducationDTO;
import com.interviewmate.userservice.dto.UpdateUserRequest;
import com.interviewmate.userservice.dto.UserResponse;
import com.interviewmate.userservice.exception.UserNotFoundException;
import com.interviewmate.userservice.model.Company;
import com.interviewmate.userservice.model.Education;
import com.interviewmate.userservice.model.User;
import com.interviewmate.userservice.repository.UserRepository;
import com.interviewmate.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImplementation implements UserService {

  private final UserRepository userRepo;
  private final UserCacheService userCacheService;


  @Override
  @Transactional
  @CachePut(value = "userProfiles", key = "#email")
  public UserResponse updateUserProfile(String email , UpdateUserRequest request) {
    User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

    // simple fields
    if (request.getName() != null) user.setName(request.getName());
    if (request.getPhoneNo() != null) user.setPhoneNumber(request.getPhoneNo());
    if (request.getYearsOfExperience() != null) user.setYearsOfExperience(request.getYearsOfExperience());
    if (request.getSkills() != null) user.setSkills(request.getSkills());

    // educations
    if (request.getEducations() != null) {
        List<Education> updated = request.getEducations().stream()
                .map(dto -> Education.builder()
                        .collegeName(dto.getCollegeName())
                        .degreeName(dto.getDegreeName())
                        .startYear(dto.getStartYear())
                        .endYear(dto.getEndYear())
                        .user(user)
                        .build())
                .collect(Collectors.toList());

        user.getEducations().clear();
        user.getEducations().addAll(updated);
    }
    if (request.getCompanies()!= null) {
        List<Company> updated = request.getCompanies().stream()
                .map(dto -> Company.builder()
                        .companyName(dto.getCompanyName())
                        .position(dto.getPosition())
                        .startYear(dto.getStartYear())
                        .endYear(dto.getEndYear())
                        .user(user)
                        .build())
                .collect(Collectors.toList());

        user.getCompanies().clear();
        user.getCompanies().addAll(updated);
    }

    User updatedUser = userRepo.save(user);
    return mapToUserResponse(updatedUser);

  }

  // @CacheEvict(value = "userProfiles", key = "#userId")
  // public void deleteUser(UUID userId) {
  //   log.info("Deleting user profile from DB and cache for userId={}", userId);
  //   userRepo.deleteById(userId);
  // }

 
  

    
  
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

  

 

  @Override
  public UserResponse getUserByEmail(String email) {
    return userCacheService.getUserProfileByEmail(email);
  }

}
