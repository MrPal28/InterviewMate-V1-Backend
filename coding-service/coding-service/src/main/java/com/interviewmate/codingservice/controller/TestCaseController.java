package com.interviewmate.codingservice.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.interviewmate.codingservice.dto.problemDto.CreateTestCaseRequest;
import com.interviewmate.codingservice.dto.problemDto.UpdateTestCaseRequest;
import com.interviewmate.codingservice.entity.TestCase;
import com.interviewmate.codingservice.service.TestCaseService;

@RestController
@RequestMapping("/api/v1/coding")
@RequiredArgsConstructor
public class TestCaseController {

    private final TestCaseService testCaseService;

      /**
     * Bulk create test cases for a problem (ADMIN)
     */
    @PostMapping("/problems/{problemId}/test-cases/bulk")
    public ResponseEntity<List<TestCase>> bulkCreateTestCases(
            @PathVariable String problemId,
            @Valid @RequestBody List<CreateTestCaseRequest> requests, @RequestHeader("X-User-Id") String userId) {

        List<TestCase> created =
                testCaseService.bulkCreateTestCases(problemId, requests, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get all test cases for a problem (ADMIN)
     */
    @GetMapping("/problems/{problemId}/test-cases")
    public ResponseEntity<List<TestCase>> getAllTestCases(
            @PathVariable String problemId , @RequestHeader("X-User-Id") String userId) {

        return ResponseEntity.ok(
                testCaseService.getAllTestCasesForProblem(problemId, userId)
        );
    }

    /**
     * Update a test case (ADMIN)
     */
    @PutMapping("/test-cases/{testCaseId}")
    public ResponseEntity<TestCase> updateTestCase(
            @PathVariable String testCaseId,
            @Valid @RequestBody UpdateTestCaseRequest request, @RequestHeader("X-User-Id") String userId) {

        return ResponseEntity.ok(
                testCaseService.updateTestCase(testCaseId, request, userId)
        );
    }

    /**
     * Delete a test case (ADMIN)
     */
    @DeleteMapping("/test-cases/{testCaseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTestCase(@PathVariable String testCaseId, @RequestHeader("X-User-Id") String userId) {
        testCaseService.deleteTestCase(testCaseId, userId);
    }
}
