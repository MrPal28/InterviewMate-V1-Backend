package com.interviewmate.resumeservice.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationResponse implements Serializable{
      private UUID id;
    private String institution;
    private String degree;
    private String field;
    private String startDate;
    private String endDate;
    private String gpa;
}
