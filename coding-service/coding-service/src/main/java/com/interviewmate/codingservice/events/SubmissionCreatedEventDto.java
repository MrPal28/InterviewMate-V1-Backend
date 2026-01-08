package com.interviewmate.codingservice.events;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.interviewmate.codingservice.dto.JudgeTestCaseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@AllArgsConstructor
@NoArgsConstructor
@Setter@Getter
@Builder
public class SubmissionCreatedEventDto {
  private String submissionId;
  private String userId;
  private String problemId;
  private Integer languageId;
  private String sourceCode;
  private List<JudgeTestCaseDto> testCases;
  private LocalDateTime createdAt;
  private UUID traceId;
}
