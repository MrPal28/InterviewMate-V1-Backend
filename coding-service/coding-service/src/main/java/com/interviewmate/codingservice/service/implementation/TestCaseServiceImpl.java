package com.interviewmate.codingservice.service.implementation;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.interviewmate.codingservice.dto.problemDto.CreateTestCaseRequest;
import com.interviewmate.codingservice.dto.problemDto.UpdateTestCaseRequest;
import com.interviewmate.codingservice.entity.Problem;
import com.interviewmate.codingservice.entity.TestCase;
import com.interviewmate.codingservice.exception.ProblemNotFoundException;
import com.interviewmate.codingservice.exception.TestCaseNotFoundException;
import com.interviewmate.codingservice.repository.ProblemRepository;
import com.interviewmate.codingservice.repository.TestCaseRepository;
import com.interviewmate.codingservice.service.TestCaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestCaseServiceImpl implements TestCaseService {

    private final TestCaseRepository testCaseRepository;
    private final ProblemRepository problemRepository;

    // ==========================
    // CREATE (BULK)
    // ==========================
    @Override
    public List<TestCase> bulkCreateTestCases(
            String problemId,
            List<CreateTestCaseRequest> requests,
            String userId) {

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new ProblemNotFoundException(problemId));

        if (!problem.getCreatedBy().equals(userId)) {
            throw new SecurityException(
                    "Only the problem creator can add test cases");
        }

        List<TestCase> testCases = requests.stream()
                .map(req -> TestCase.builder()
                        .problemId(problemId)
                        .createdBy(userId)
                        .stdin(req.getStdin())
                        .stdout(req.getStdout())
                        .hidden(req.isHidden())
                        .build())
                .toList();

        return testCaseRepository.saveAll(testCases);
    }


    // ==========================
    // READ (ALL)
    // ==========================
    @Override
    public List<TestCase> getTestCasesForProblem(String problemId , boolean hidden) {

        validateProblem(problemId);
        return testCaseRepository.findByProblemIdAndHidden(problemId, hidden);
    }

    // ==========================
    // READ (PUBLIC ONLY)
    // ==========================
    @Override
    public List<TestCase> getPublicTestCasesForProblem(String problemId) {

        validateProblem(problemId);
        return testCaseRepository.findByProblemIdAndHiddenFalse(problemId);
    }

    // ==========================
    // UPDATE
    // ==========================
    @Override
    public TestCase updateTestCase(
            String testCaseId,
            UpdateTestCaseRequest request,
            String userId) {

        TestCase existing = testCaseRepository.findById(testCaseId)
                .orElseThrow(() -> new TestCaseNotFoundException(testCaseId));

        Problem problem = problemRepository.findById(existing.getProblemId())
                .orElseThrow(() -> new ProblemNotFoundException(existing.getProblemId()));

        if (!problem.getCreatedBy().equals(userId)) {
            throw new SecurityException(
                    "Only the problem creator can update test cases");
        }

        existing.setStdin(request.getStdin());
        existing.setStdout(request.getStdout());
        existing.setHidden(request.isHidden());

        return testCaseRepository.save(existing);
    }


    // ==========================
    // DELETE
    // ==========================
    @Override
    public void deleteTestCase(String testCaseId, String userId) {

        TestCase existing = testCaseRepository.findById(testCaseId)
                .orElseThrow(() -> new TestCaseNotFoundException(testCaseId));

        Problem problem = problemRepository.findById(existing.getProblemId())
                .orElseThrow(() -> new ProblemNotFoundException(existing.getProblemId()));

        if (!problem.getCreatedBy().equals(userId)) {
            throw new SecurityException(
                    "Only the problem creator can delete test cases");
        }

        testCaseRepository.delete(existing);
    }


    // ==========================
    // INTERNAL
    // ==========================
    private void validateProblem(String problemId) {

        if (!problemRepository.existsById(problemId)) {
            throw new ProblemNotFoundException(problemId);
        }
    }

    @Override
    public void deleteTestCasesByProblemId(String problemId) {
        testCaseRepository.deleteAllByProblemId(problemId);
    }

    @Override
    public List<TestCase> getAllTestCasesForProblem(String problemId, String userId) {

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new ProblemNotFoundException(problemId));

        if (!problem.getCreatedBy().equals(userId)) {
            throw new SecurityException(
                    "You are not allowed to view test cases for this problem");
        }

        return testCaseRepository.findByProblemId(problemId);
    }

}
