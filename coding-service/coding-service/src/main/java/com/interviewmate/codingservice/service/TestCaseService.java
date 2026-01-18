package com.interviewmate.codingservice.service;

import java.util.List;


import com.interviewmate.codingservice.dto.problemDto.CreateTestCaseRequest;
import com.interviewmate.codingservice.dto.problemDto.UpdateTestCaseRequest;
import com.interviewmate.codingservice.entity.TestCase;

public interface TestCaseService {

    List<TestCase> bulkCreateTestCases(
            String problemId,
            List<CreateTestCaseRequest> requests, String userId);

    List<TestCase> getTestCasesForProblem(String problemId, boolean hidden);

    List<TestCase> getPublicTestCasesForProblem(String problemId);

    TestCase updateTestCase(String testCaseId, UpdateTestCaseRequest request, String userId);

    void deleteTestCase(String testCaseId, String userId);

    void deleteTestCasesByProblemId(String problemId);

    List<TestCase> getAllTestCasesForProblem(String problemId, String userId);

}
