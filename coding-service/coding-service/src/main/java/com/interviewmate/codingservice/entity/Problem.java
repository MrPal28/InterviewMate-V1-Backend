package com.interviewmate.codingservice.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document(collection = "problems")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndex(name = "unique_slug_idx", def = "{'slug': 1}", unique = true)
@CompoundIndex(name = "title_idx", def = "{'title': 1}")
@CompoundIndex(name = "difficulty_idx", def = "{'difficulty': 1}")
@CompoundIndex(name = "tags_idx", def = "{'tags': 1}")
@CompoundIndex(name = "companies_idx", def = "{'companies': 1}")
public class Problem {

    @Id
    private String id;

    private String title;
    private String slug;

    private String description;

    private String difficulty;   // EASY, MEDIUM, HARD
    private boolean premium;

    private List<String> tags;       // ["Array", "DP"]
    private List<String> companies;  // ["Google", "Amazon"]


    private List<SampleIO> sample;
    private String createdBy; // x-user-id



}
