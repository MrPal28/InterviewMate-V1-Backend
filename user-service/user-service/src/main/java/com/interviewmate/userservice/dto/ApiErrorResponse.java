package com.interviewmate.userservice.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {

    private boolean success;
    private String message;
    private String errorCode;
    private Instant timestamp;
}
