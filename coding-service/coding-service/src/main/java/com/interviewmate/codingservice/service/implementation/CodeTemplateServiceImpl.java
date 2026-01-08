package com.interviewmate.codingservice.service.implementation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.interviewmate.codingservice.dto.problemDto.CodeTemplateResponse;
import com.interviewmate.codingservice.dto.problemDto.CreateCodeTemplateRequest;
import com.interviewmate.codingservice.entity.CodeTemplate;
import com.interviewmate.codingservice.mapper.ProblemMapper;
import com.interviewmate.codingservice.service.CodeTemplateService;
import com.interviewmate.codingservice.repository.CodeTemplateRepository;

@Service
public class CodeTemplateServiceImpl implements CodeTemplateService {

  private final CodeTemplateRepository codeTemplateRepository;
  private final ProblemMapper problemMapper;

  public CodeTemplateServiceImpl(CodeTemplateRepository codeTemplateRepository, ProblemMapper problemMapper) {
    this.codeTemplateRepository = codeTemplateRepository;
    this.problemMapper = problemMapper;
  }

  @Override
  public List<CodeTemplateResponse> createCodeTemplates(
      String problemId,
      List<CreateCodeTemplateRequest> requests) {

    if (requests == null || requests.size() < 2) {
      throw new IllegalArgumentException(
          "At least two language templates are required");
    }

    Set<String> languages = new HashSet<>();
    List<CodeTemplate> entities = new ArrayList<>();

    for (CreateCodeTemplateRequest request : requests) {

      String language = request.getLanguage().toUpperCase();

      if (!languages.add(language)) {
        throw new IllegalArgumentException(
            "Duplicate code template for language: " + language);
      }

      CodeTemplate entity = new CodeTemplate();
      entity.setProblemId(problemId);
      entity.setLanguageId(language);
      entity.setTemplate(request.getTemplate());
      entities.add(entity);
    }
    // Save ENTITIES
    List<CodeTemplate> saved = codeTemplateRepository.saveAll(entities);
    // Convert to DTOs AFTER saving
    return saved.stream().map(e->problemMapper.toCodeTemplateDto(e)).collect(Collectors.toList());
  }

  @Override
  public List<CodeTemplateResponse> getCodeTemplatesByProblemId(String problemId) {
    List<CodeTemplate> entities = codeTemplateRepository.findByProblemId(problemId);
    return entities.stream().map(e->problemMapper.toCodeTemplateDto(e)).collect(Collectors.toList());
  }

  @Override
  public void deleteCodeTemplatesByProblemId(String problemId) {
    codeTemplateRepository.deleteAllByProblemId(problemId);
  }

  @Override
  public List<CodeTemplateResponse> replaceTemplates(String problemId, List<CreateCodeTemplateRequest> requests) {
    deleteCodeTemplatesByProblemId(problemId);
    return createCodeTemplates(problemId, requests);
  }
  
}
