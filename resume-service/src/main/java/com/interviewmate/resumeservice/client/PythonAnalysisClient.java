package com.interviewmate.resumeservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.interviewmate.resumeservice.dto.AnalysisResponse;
import com.interviewmate.resumeservice.dto.PythonAnalysisRequest;
import com.interviewmate.resumeservice.dto.PythonAnalysisRequestData;

@FeignClient(name = "python-service", url = "${python.api.url}")
public interface PythonAnalysisClient {

    @PostMapping("/analyzewithdocument/")
    AnalysisResponse analyzeResume(@RequestBody PythonAnalysisRequest request);

    @PostMapping("/analyzewithjson/")
    AnalysisResponse analyzeStoredResume(@RequestBody PythonAnalysisRequestData requestData);
}
