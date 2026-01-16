package com.interviewmate.codingservice.service;

import java.util.List;

import com.interviewmate.codingservice.dto.problemDto.CodeTemplateResponse;
import com.interviewmate.codingservice.dto.problemDto.CreateCodeTemplateRequest;

public interface CodeTemplateService {
  List<CodeTemplateResponse> createCodeTemplates( String problemId ,List<CreateCodeTemplateRequest> requests);
  List<CodeTemplateResponse> getCodeTemplatesByProblemId(String problemId);
  void deleteCodeTemplatesByProblemId(String problemId);
      List<CodeTemplateResponse> replaceTemplates(
        String problemId,
        List<CreateCodeTemplateRequest> requests
    );

  }
