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
public class CompanyDTO implements Serializable {
  private String companyName;
  private String position;
  private Integer startYear;
  private Integer endYear;

 
}