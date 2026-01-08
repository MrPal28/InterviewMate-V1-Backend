package com.interviewmate.userservice.dto;

import java.util.List;

import lombok.Data;

@Data
public class UpdateUserRequest {

    private String name;
    private String phoneNo;
    private List<EducationDTO> educations;
    // --- EMPLOYEE FIELDS ---
    private List<CompanyDTO> companies;
    private Integer yearsOfExperience;
    private String skills;
	

}
