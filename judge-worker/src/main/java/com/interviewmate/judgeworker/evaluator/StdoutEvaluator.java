package com.interviewmate.judgeworker.evaluator;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.interviewmate.judgeworker.dto.JudgeTestCaseDto;
import com.interviewmate.judgeworker.dto.TestCaseResult;

import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class StdoutEvaluator {

    public TestCaseResult evaluateSingle(int index, String actual, JudgeTestCaseDto tc) {

        String actualNorm = normalize(actual);

        // Expected output can have multiple valid answers
        // Example: "0 4 | 2 3"
        String expectedRaw = tc.getStdout();

        List<String> expectedOptions = Arrays.stream(expectedRaw.split("\\|"))
                .map(this::normalize)
                .toList();

        boolean passed = expectedOptions.contains(actualNorm);

        log.debug(
            "[EVALUATE MULTI] index={} actual='{}' expectedOptions={} passed={}",
            index, actualNorm, expectedOptions, passed
        );

        return TestCaseResult.builder()
                .index(index)
                .passed(passed)
                .expected(expectedRaw)   // keep full expected for debugging
                .actual(actualNorm)
                .build();
    }

    private String normalize(String s) {
        return s == null ? "" : s.trim().replaceAll("\\s+", " ");
    }
}
