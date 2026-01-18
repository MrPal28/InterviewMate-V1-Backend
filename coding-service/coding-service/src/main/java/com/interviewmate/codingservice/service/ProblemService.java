package com.interviewmate.codingservice.service;


import org.springframework.data.domain.Page;

import com.interviewmate.codingservice.dto.problemDto.BulkCreateProblemRequest;
import com.interviewmate.codingservice.dto.problemDto.BulkCreateProblemResponse;
import com.interviewmate.codingservice.dto.problemDto.CreateProblemRequest;
import com.interviewmate.codingservice.dto.problemDto.ProblemResponse;
import com.interviewmate.codingservice.dto.problemDto.UpdateProblemRequest;

public interface ProblemService {
  ProblemResponse createProblem(CreateProblemRequest problem , String userId);
  ProblemResponse getProblemById(String id);
  Page<ProblemResponse> getProblems(int page,
        int size,
        String search,
        String difficulty,
        String tag);
  void deleteProblem(String id , String userId);
  BulkCreateProblemResponse bulkCreateProblems(BulkCreateProblemRequest request , String userId);
  ProblemResponse updateProblem(String id,UpdateProblemRequest request , String userId);
}
