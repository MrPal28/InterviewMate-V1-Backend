package com.interviewmate.codingservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "code_templates")
public class CodeTemplate {
  @Id
  private String id;
  private String problemId;
  private String languageId;
  private String template;
}
