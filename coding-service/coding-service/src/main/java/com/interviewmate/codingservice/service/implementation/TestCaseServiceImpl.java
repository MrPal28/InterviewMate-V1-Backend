package com.interviewmate.codingservice.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.interviewmate.codingservice.dto.problemDto.CreateTestCaseRequest;
import com.interviewmate.codingservice.dto.problemDto.UpdateTestCaseRequest;
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
            List<CreateTestCaseRequest> requests) {

        validateProblem(problemId);

        List<TestCase> testCases = requests.stream()
                .map(req -> TestCase.builder()
                        .problemId(problemId)
                        .stdin(req.getStdin())           //  NEW
                        .stdout(req.getStdout())         // NEW
                        .hidden(req.isHidden())          //  NEW
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
            UpdateTestCaseRequest request) {

        TestCase existing = testCaseRepository.findById(testCaseId)
                .orElseThrow(() -> new TestCaseNotFoundException(testCaseId));

        existing.setStdin(request.getStdin());
        existing.setStdout(request.getStdout());
        existing.setHidden(request.isHidden());

        return testCaseRepository.save(existing);
    }

    // ==========================
    // DELETE
    // ==========================
    @Override
    public void deleteTestCase(String testCaseId) {

        if (!testCaseRepository.existsById(testCaseId)) {
            throw new TestCaseNotFoundException(testCaseId);
        }

        testCaseRepository.deleteById(testCaseId);
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
    public List<TestCase> getAllTestCasesForProblem(String problemId) {
        validateProblem(problemId);
        return testCaseRepository.findByProblemId(problemId);
    }
}
