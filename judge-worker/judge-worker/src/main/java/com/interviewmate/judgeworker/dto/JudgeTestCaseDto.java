package com.interviewmate.judgeworker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgeTestCaseDto {

    /** Exact STDIN passed to Judge0 */
    private String stdin;

    /** Expected STDOUT for comparison */
    private String stdout;

    /** Hidden from user or not */
    private boolean hidden;
}
