package com.interviewmate.codingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SampleIO {
    private String stdin;
    private String stdout;
    private String explanation;
}
