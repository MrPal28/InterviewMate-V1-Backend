package com.interviewmate.userservice.service.implementation;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.interviewmate.userservice.config.CustomUserDetails;
import com.interviewmate.userservice.dto.CompanyDTO;
import com.interviewmate.userservice.dto.EducationDTO;
import com.interviewmate.userservice.dto.LoginRequestDTO;
import com.interviewmate.userservice.dto.LoginResponse;
import com.interviewmate.userservice.dto.NewUserEvent;
import com.interviewmate.userservice.dto.RegisterRequestDTO;
import com.interviewmate.userservice.dto.UserResponse;
import com.interviewmate.userservice.eventproducerandconsumer.NewUserRegistrationEventProducer;
import com.interviewmate.userservice.exception.InvalidCredentialsException;
import com.interviewmate.userservice.exception.UserAlreadyExistsException;
import com.interviewmate.userservice.exception.UserNotFoundException;
import com.interviewmate.userservice.model.Company;
import com.interviewmate.userservice.model.Education;
import com.interviewmate.userservice.model.User;
import com.interviewmate.userservice.repository.UserRepository;
import com.interviewmate.userservice.service.AuthService;
import com.interviewmate.userservice.service.OTPService;
import com.interviewmate.userservice.utils.JwtService;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final NewUserRegistrationEventProducer newUserEventProducer;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final OTPService otpService;

  @Override
  public UserResponse register(RegisterRequestDTO request) {
    userRepo.findByEmail(request.getEmail()).ifPresent(u -> {
			 throw new UserAlreadyExistsException(request.getEmail());
		});
		User user = mapToUserEntity(request);
		userRepo.save(user);

    UserResponse userResponse = mapToUserResponse(user);
    NewUserEvent userEvent = mapToUserEvent(user);
    newUserEventProducer.sendMessageToKafka(user.getEmail(), userEvent);
    return userResponse;
  }
  
  @Override
  public LoginResponse login(LoginRequestDTO request) {
    try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

    } catch (AuthenticationException ex) {
        throw new InvalidCredentialsException();
    }

    CustomUserDetails userDetails =
            (CustomUserDetails) userDetailsService.loadUserByUsername(request.getEmail());

    final String jwtToken = jwtService.generateToken(userDetails);

    return LoginResponse.builder()
            .id(userDetails.getId())
            .email(userDetails.getUsername())
            .role(userDetails.getRole())
            .token(jwtToken)
            .build();
}

  @Override
public void resetPassword(String email, String otp, String newPassword) {

  // Will throw exception if invalid
  otpService.verifyOtp(email, otp);

  User user = userRepo.findByEmail(email)
      .orElseThrow(() -> new UserNotFoundException(email));

  user.setPassword(passwordEncoder.encode(newPassword));
  userRepo.save(user);
}

  private User mapToUserEntity(RegisterRequestDTO request) {
    User user = User.builder()
        .name(request.getName())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword())) 
        .phoneNumber(request.getPhoneNumber())
        .role(request.getRole())
        .yearsOfExperience(request.getYearsOfExperience())
        .skills(request.getSkills())
        .build();

    if (request.getEducations() != null && !request.getEducations().isEmpty()) {
      List<Education> educations = request.getEducations().stream()
          .map(dto -> Education.builder()
              .collegeName(dto.getCollegeName())
              .degreeName(dto.getDegreeName())
              .startYear(dto.getStartYear())
              .endYear(dto.getEndYear())
              .user(user) // set parent
              .build())
          .toList();
      user.setEducations(educations);
    }
    if (request.getCompanies() != null && !request.getCompanies().isEmpty()) {
      List<Company> companies = request.getCompanies().stream()
          .map(dto -> Company.builder()
              .companyName(dto.getCompanyName())
              .position(dto.getPosition())
              .startYear(dto.getStartYear())
              .endYear(dto.getEndYear())
              .user(user) // set parent
              .build())
          .toList();
      user.setCompanies(companies);
    }

    return user;
  }

  private NewUserEvent mapToUserEvent(User user) {
    return NewUserEvent.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .build();
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
