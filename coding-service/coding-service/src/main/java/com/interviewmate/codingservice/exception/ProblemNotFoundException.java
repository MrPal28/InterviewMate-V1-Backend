package com.interviewmate.codingservice.exception;

public class ProblemNotFoundException extends RuntimeException {
    public ProblemNotFoundException(String problemId) {
        super("Problem not found: " + problemId);
    }
}