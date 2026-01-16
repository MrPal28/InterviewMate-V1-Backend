package com.interviewmate.codingservice.events;

import java.time.LocalDateTime;
import java.util.UUID;

import com.interviewmate.codingservice.constants.SubmissionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmissionResultevent {
  private String submissionId;
  private SubmissionStatus status; 
  private String stdout;
  private String stderr;
  private String judgeToken;
  private Integer durationMs;
  private LocalDateTime finishedAt;
  private UUID traceId;
}
