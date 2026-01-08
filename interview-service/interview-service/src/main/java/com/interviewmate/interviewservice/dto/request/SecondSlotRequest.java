package com.interviewmate.interviewservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SecondSlotRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String sessionId;

    @Min(1)
    private int remanning;

    @NotBlank
    private String level;
}
