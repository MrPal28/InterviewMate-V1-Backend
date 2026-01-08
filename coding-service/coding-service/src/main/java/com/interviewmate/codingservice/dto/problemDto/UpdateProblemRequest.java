package com.interviewmate.codingservice.dto.problemDto;

import java.util.List;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProblemRequest {

    private String title;

    private String description;

    private String difficulty; // EASY, MEDIUM, HARD

    private Boolean premium;

    private List<String> tags;
    private List<String> companies;

    // Optional: update templates only if provided
    @Valid
    private List<CreateCodeTemplateRequest> codeTemplates;

    @Valid
    private List<SampleIODto> sample;
}
