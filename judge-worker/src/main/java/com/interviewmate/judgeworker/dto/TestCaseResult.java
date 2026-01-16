package com.interviewmate.judgeworker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseResult {

    private int index;
    private boolean passed;
    private String expected;
    private String actual;
}
