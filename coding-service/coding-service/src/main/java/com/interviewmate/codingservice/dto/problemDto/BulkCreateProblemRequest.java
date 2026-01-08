package com.interviewmate.codingservice.dto.problemDto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkCreateProblemRequest {

    @NotEmpty
    @Valid
    private List<CreateProblemRequest> problems;
}
