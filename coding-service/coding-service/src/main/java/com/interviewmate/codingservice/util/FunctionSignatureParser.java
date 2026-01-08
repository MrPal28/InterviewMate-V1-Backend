package com.interviewmate.codingservice.util;

import org.springframework.stereotype.Component;

@Component
public class FunctionSignatureParser {

    public String extractFunctionName(String signature) {
        int start = signature.indexOf(' ') + 1;
        int end = signature.indexOf('(');
        return signature.substring(start, end).trim();
    }

    public int extractParameterCount(String signature) {
        int start = signature.indexOf('(') + 1;
        int end = signature.indexOf(')');
        String params = signature.substring(start, end).trim();
        return params.isEmpty() ? 0 : params.split(",").length;
    }
  
}
