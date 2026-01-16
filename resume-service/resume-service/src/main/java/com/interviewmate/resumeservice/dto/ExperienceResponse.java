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
public class ExperienceResponse implements Serializable{
      private UUID id;
    private String company;
    private String position;
    private String startDate;
    private String endDate;
    private boolean current;
    private String description;
}
