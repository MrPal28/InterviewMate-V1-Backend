package com.interviewmate.codingservice.dto.problemDto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkCreateProblemResponse {

    private List<ProblemResponse> createdProblems;

    private List<BulkCreateError> errors;
}
