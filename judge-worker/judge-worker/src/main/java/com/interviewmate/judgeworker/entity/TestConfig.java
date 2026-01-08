package com.interviewmate.judgeworker.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestConfig {

    private String key; // two-sum-v1
    private List<TestCase> testCases;
}

