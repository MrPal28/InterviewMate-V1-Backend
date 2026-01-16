package com.interviewmate.codingservice.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interviewmate.codingservice.dto.problemDto.BulkCreateError;
import com.interviewmate.codingservice.dto.problemDto.BulkCreateProblemRequest;
import com.interviewmate.codingservice.dto.problemDto.BulkCreateProblemResponse;
import com.interviewmate.codingservice.dto.problemDto.CodeTemplateResponse;
import com.interviewmate.codingservice.dto.problemDto.CreateCodeTemplateRequest;
import com.interviewmate.codingservice.dto.problemDto.CreateProblemRequest;
import com.interviewmate.codingservice.dto.problemDto.ProblemResponse;
import com.interviewmate.codingservice.dto.problemDto.UpdateProblemRequest;
import com.interviewmate.codingservice.entity.Problem;
import com.interviewmate.codingservice.exception.ProblemNotFoundException;
import com.interviewmate.codingservice.mapper.ProblemMapper;
import com.interviewmate.codingservice.repository.ProblemRepository;
import com.interviewmate.codingservice.service.CodeTemplateService;
import com.interviewmate.codingservice.service.ProblemService;
import com.interviewmate.codingservice.service.TestCaseService;

import jakarta.ws.rs.NotFoundException;

@Service
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository problemRepository;
    private final ProblemMapper problemMapper;
    private final CodeTemplateService codeTemplateService;
    private final TestCaseService testCaseService;

    public ProblemServiceImpl(ProblemRepository problemRepository, ProblemMapper problemMapper,CodeTemplateService codeTemplateService,TestCaseService testCaseService) {
        this.problemRepository = problemRepository;
        this.problemMapper = problemMapper;
        this.codeTemplateService = codeTemplateService;
        this.testCaseService = testCaseService;
    }

    @Override
    public ProblemResponse createProblem(CreateProblemRequest request) {
        if (problemRepository.existsBySlug(request.getSlug())) {
            throw new IllegalArgumentException(
                    "Problem already exists with slug: " + request.getSlug());
        }
        Problem problem = problemMapper.toEntity(request);
        Problem savedProblem = problemRepository.save(problem);
        List<CreateCodeTemplateRequest> codeTemplates = request.getCodeTemplates();
        List<CodeTemplateResponse> codeTemplateEntities = codeTemplateService.createCodeTemplates(savedProblem.getId(),codeTemplates);
        return problemMapper.toDto(savedProblem,codeTemplateEntities);
    }

    @Override
    public ProblemResponse getProblemById(String id) {
        return problemMapper.toDto(
                problemRepository.findById(id).orElseThrow(() -> new RuntimeException("Problem not found")),
                codeTemplateService.getCodeTemplatesByProblemId(id));
    }

    @Override
    public Page<ProblemResponse> getProblems(int page,
            int size,
            String search,
            String difficulty,
            String tag) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Problem> problemPage;

        if (search != null && difficulty != null) {
            problemPage = problemRepository
                    .findByTitleContainingIgnoreCaseAndDifficulty(
                            search, difficulty, pageable);
        } else if (search != null && tag != null) {
            problemPage = problemRepository
                    .findByTitleContainingIgnoreCaseAndTagsContaining(
                            search, tag, pageable);
        } else if (search != null) {
            problemPage = problemRepository
                    .findByTitleContainingIgnoreCase(search, pageable);
        } else if (difficulty != null) {
            problemPage = problemRepository
                    .findByDifficulty(difficulty, pageable);
        } else if (tag != null) {
            problemPage = problemRepository
                    .findByTagsContaining(tag, pageable);
        } else {
            problemPage = problemRepository.findAll(pageable);
        }

        return problemPage.map(problem -> {
            return problemMapper.toDto(problem,codeTemplateService.getCodeTemplatesByProblemId(problem.getId()));
        });
    }

    @Transactional
    @Override
    public void deleteProblem(String id) {

        if (!problemRepository.existsById(id)) {
            throw new ProblemNotFoundException(id);
        }
        codeTemplateService.deleteCodeTemplatesByProblemId(id);
        testCaseService.deleteTestCasesByProblemId(id);
        problemRepository.deleteById(id);
    }


    @Override
    public BulkCreateProblemResponse bulkCreateProblems(BulkCreateProblemRequest request) {
        List<ProblemResponse> createdProblems = new ArrayList<>();
        List<BulkCreateError> errors = new ArrayList<>();

    for (CreateProblemRequest problemRequest : request.getProblems()) {
        try {
            // 1. Uniqueness check
            if (problemRepository.existsBySlug(problemRequest.getSlug())) {
                errors.add(
                    BulkCreateError.builder()
                        .slug(problemRequest.getSlug())
                        .reason("Duplicate slug")
                        .build()
                );
                continue;
            }

            // 2. Save problem
            Problem problem =
                    problemRepository.save(
                        problemMapper.toEntity(problemRequest)
                    );
            List<CodeTemplateResponse> codeTemplates = codeTemplateService.createCodeTemplates(problem.getId(),problemRequest.getCodeTemplates());

            // 4. Aggregate response
            createdProblems.add(
                problemMapper.toDto(problem,codeTemplates)
            );

        } catch (Exception ex) {
            errors.add(
                BulkCreateError.builder()
                    .slug(problemRequest.getSlug())
                    .reason(ex.getMessage())
                    .build()
            );
        }
    }

    return BulkCreateProblemResponse.builder()
            .createdProblems(createdProblems)
            .errors(errors)
            .build();
    }

    @Override
public ProblemResponse updateProblem(String problemId, UpdateProblemRequest request) {

    Problem problem = problemRepository.findById(problemId)
        .orElseThrow(() -> new NotFoundException("Problem not found"));

    // 1. Update problem fields
    problemMapper.updateEntityFromDto(problem, request);
    problemRepository.save(problem);

    // 2. Update templates if provided
    List<CodeTemplateResponse> templateResponses;

    if (request.getCodeTemplates() != null) {
        templateResponses =
            codeTemplateService.replaceTemplates(
                problemId, 
                request.getCodeTemplates()
            );
    } else {
        templateResponses =
            codeTemplateService.getCodeTemplatesByProblemId(problemId);
    }

    // 3. Return FULL response
    return problemMapper.toDto(problem, templateResponses);
}

    
}
