package com.interviewmate.resumeservice.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse implements Serializable {
  private UUID id;
    private String name;
    private String description;
    private List<String> technologies;
    private String link;
}
