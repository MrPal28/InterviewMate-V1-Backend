package com.interviewmate.codingservice.dto.problemDto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemResponse {

    private String id;
    private String title;
    private String slug;

    private String description;
    private String difficulty;
    private boolean premium;

    private List<String> tags;
    private List<String> companies;

    private List<CodeTemplateResponse> codeTemplates;
    private List<SampleIODto> sample;
}
