package com.interviewmate.codingservice.dto.problemDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkCreateError {

    private String slug;
    private String reason;
}
