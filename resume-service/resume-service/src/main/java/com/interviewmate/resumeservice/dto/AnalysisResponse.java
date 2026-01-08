package com.interviewmate.resumeservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnalysisResponse {
    private String userid;
    private int score;
    private int atsCompatibility;
    private List<String> strengths;
    private List<String> improvements;
    private List<String> keywords;
    private List<Suggestion> suggestions;
}