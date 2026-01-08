package com.interviewmate.resumeservice.service;

import com.interviewmate.resumeservice.dto.AnalysisResponse;
import com.interviewmate.resumeservice.dto.PythonAnalysisRequest;
import com.interviewmate.resumeservice.dto.PythonAnalysisRequestData;

public interface PythonAnalysisService {
  AnalysisResponse analyzeResume(PythonAnalysisRequest request);
  AnalysisResponse analyzeStoredResume(PythonAnalysisRequestData requestData);
}
