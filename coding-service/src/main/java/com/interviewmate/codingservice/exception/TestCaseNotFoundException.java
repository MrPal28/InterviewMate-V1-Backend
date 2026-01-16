package com.interviewmate.codingservice.exception;

public class TestCaseNotFoundException extends RuntimeException {
    public TestCaseNotFoundException(String id) {
        super("Test case not found: " + id);
    }
}