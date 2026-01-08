package com.interviewmate.resumeservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class PythonAnalysisRequestData {
  private ResumeAnalysisDTO resume;
  private String jobDescription;
}
