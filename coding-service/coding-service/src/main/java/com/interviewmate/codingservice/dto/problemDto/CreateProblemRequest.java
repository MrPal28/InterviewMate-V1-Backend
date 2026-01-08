package com.interviewmate.codingservice.dto.problemDto;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProblemRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String slug; // e.g. "two-sum"

    @NotBlank
    private String description;

    @NotBlank
    private String difficulty; // EASY, MEDIUM, HARD

    private boolean premium;

    private List<String> tags;
    private List<String> companies;

    @Valid
    @NotNull
    @Size(min = 2, message = "At least two language templates are required")
    private List<CreateCodeTemplateRequest> codeTemplates;

    @Valid
    private List<SampleIODto> sample;
}
