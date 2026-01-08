package com.interviewmate.codingservice.dto.problemDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTestCaseRequest {

    private String stdin;
    private String stdout;
    private boolean hidden;
}

