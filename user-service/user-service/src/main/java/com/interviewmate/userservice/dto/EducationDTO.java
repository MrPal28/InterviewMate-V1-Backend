package com.interviewmate.userservice.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EducationDTO implements Serializable {
    private String collegeName;
    private String degreeName;
    private Integer startYear;
    private Integer endYear;
}
