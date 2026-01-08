package com.interviewmate.resumeservice.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalInfoDTO implements Serializable {
    private String fullName;
    private String email;
    private String phone;
    private String location;
    private String linkedIn;
    private String portfolio;
}