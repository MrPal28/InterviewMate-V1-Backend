package com.interviewmate.codingservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interviewmate.codingservice.dto.PageResponse;
import com.interviewmate.codingservice.dto.problemDto.BulkCreateProblemRequest;
import com.interviewmate.codingservice.dto.problemDto.BulkCreateProblemResponse;
import com.interviewmate.codingservice.dto.problemDto.CreateProblemRequest;
import com.interviewmate.codingservice.dto.problemDto.ProblemResponse;
import com.interviewmate.codingservice.dto.problemDto.UpdateProblemRequest;
import com.interviewmate.codingservice.mapper.PageMapper;
import com.interviewmate.codingservice.service.ProblemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/coding")
public class ProblemController {
  private final ProblemService problemService;

  public ProblemController(ProblemService problemService) {
    this.problemService = problemService;
  }

  @PostMapping("/problems")
  public ResponseEntity<ProblemResponse> createProblem(
      @Valid @RequestBody CreateProblemRequest request) {
    return ResponseEntity.ok(problemService.createProblem(request));
  }

  @GetMapping("/problems/{id}")
  public ResponseEntity<ProblemResponse> getProblem(@PathVariable String id) {
    return ResponseEntity.ok(problemService.getProblemById(id));
  }

  @GetMapping("/problems")
  public ResponseEntity<PageResponse<ProblemResponse>> getProblems(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(required = false) String search,
      @RequestParam(required = false) String difficulty,
      @RequestParam(required = false) String tag) {
    Page<ProblemResponse> problemPage = problemService.getProblems(page, size, search, difficulty, tag);

    return ResponseEntity.ok(
        PageMapper.toPageResponse(problemPage));
  }


  @DeleteMapping("/problems/{id}")
  public ResponseEntity<Void> deleteProblem(@PathVariable String id) {
    problemService.deleteProblem(id);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/problems/bulk")
  public ResponseEntity<BulkCreateProblemResponse> bulkCreateProblems(
      @Valid @RequestBody BulkCreateProblemRequest request) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(problemService.bulkCreateProblems(request));
  }

  @PutMapping("/problems/{id}")
  public ResponseEntity<ProblemResponse> updateProblem(
      @PathVariable String id,
      @Valid @RequestBody UpdateProblemRequest request) {
    return ResponseEntity.ok(problemService.updateProblem(id, request));
  }
  

}
