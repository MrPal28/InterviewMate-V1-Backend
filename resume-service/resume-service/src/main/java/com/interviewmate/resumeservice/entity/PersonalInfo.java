package com.interviewmate.resumeservice.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalInfo {
    private String fullName;
    private String email;
    private String phone;
    private String location;
    private String linkedIn;
    private String portfolio;
}
