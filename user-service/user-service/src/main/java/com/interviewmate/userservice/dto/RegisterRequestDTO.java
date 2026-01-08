package com.interviewmate.userservice.dto;


import java.util.List;

import com.interviewmate.userservice.constants.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {


    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    // --- STUDENT FIELDS ---
    private List<EducationDTO> educations;
    // --- EMPLOYEE FIELDS ---
    private List<CompanyDTO> companies;
    private Integer yearsOfExperience;
    private String skills; // comma separated

}
