package com.interviewmate.judgeworker.dto;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationResult {

    private String verdict;

    public static EvaluationResult fromTestCaseResults(
            List<TestCaseResult> results
    ) {
        boolean allPassed =
            results.stream().allMatch(TestCaseResult::isPassed);

        return new EvaluationResult(
            allPassed ? "ACCEPTED" : "WRONG_ANSWER"
        );
    }
}
