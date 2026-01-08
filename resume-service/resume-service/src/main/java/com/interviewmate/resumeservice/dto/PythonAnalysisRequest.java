package com.interviewmate.resumeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PythonAnalysisRequest {
  private String userid;
  private String url;
  private String jobDescription;
}
