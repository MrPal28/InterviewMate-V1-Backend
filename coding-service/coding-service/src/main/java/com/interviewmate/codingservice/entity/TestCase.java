package com.interviewmate.codingservice.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "test_cases")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndex(
    name = "idx_problem_hidden",
    def = "{'problemId': 1, 'hidden': 1}"
)
public class TestCase {

    @Id
    private String id;

    /** Reference to problem */
    private String problemId; //FK

    /**
     * EXACT input fed to the program via STDIN.
     * Must be newline-delimited.
     * Example:
     * 5
     * 4
     * 1 5 3 7
     * 8
     */
    private String stdin;

    /**
     * EXACT expected output from STDOUT.
     * Newlines matter.
     * Example:
     * 0 3
     * 0 2
     */
    private String stdout;

    /**
     * true  -> hidden from user (judge-only)
     * false -> visible sample test case
     */
    private boolean hidden;
}