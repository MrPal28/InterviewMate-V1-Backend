package com.interviewmate.codingservice.dto;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionWithEmitter {
  private String submissionId;
  private SseEmitter emitter;
}
