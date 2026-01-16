package com.interviewmate.judgeworker.dto;
import lombok.Data;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



@Data
public class SubmissionExecutionContext {

    private String submissionId;
    private String sourceCode;
    private int languageId;
    private String traceId;

    private List<JudgeTestCaseDto> testCases;
    private int currentIndex = 0;

    // Thread-safe list (important)
    private final List<TestCaseResult> results =
            Collections.synchronizedList(new ArrayList<>());

    // Guard to ensure finalize is called exactly once
    private boolean finalized = false;

    // =========================
    // Testcase navigation
    // =========================

    public boolean hasNext() {
        return currentIndex < testCases.size();
    }

    public JudgeTestCaseDto currentTestCase() {
        return testCases.get(currentIndex);
    }

    public void moveNext() {
        currentIndex++;
    }

    public int totalTestCases() {
        return testCases.size();
    }

    // =========================
    // Finalization guard
    // =========================

    public synchronized boolean markFinalized() {
        if (finalized) {
            return false;
        }
        finalized = true;
        return true;
    }
}
