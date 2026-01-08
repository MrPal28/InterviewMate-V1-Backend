package com.interviewmate.userservice.dto;



import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserResponse implements Serializable {
    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
     // --- Student Fields ---
    private List<EducationDTO> educations;  // bachelor, master, PhD

    // --- Employee Fields ---
    private List<CompanyDTO> companies; // list of companies worked at
    private Integer yearsOfExperience;
    private String skills;
}
