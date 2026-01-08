package com.interviewmate.codingservice.controller;

import com.interviewmate.codingservice.dto.CreateSubmissionRequest;
import com.interviewmate.codingservice.dto.RunWithEmitter;
import com.interviewmate.codingservice.dto.SubmissionResponse;
import com.interviewmate.codingservice.dto.SubmissionWithEmitter;
import com.interviewmate.codingservice.service.SubmissionService;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/v1/coding/submissions")
public class SubmissionController {

        private static final Logger log = LoggerFactory.getLogger(SubmissionController.class);

        private final SubmissionService submissionService;

        public SubmissionController(SubmissionService submissionService) {
                this.submissionService = submissionService;
        }

        /**
         * Submit code → Opens SSE channel immediately.
         * Result lifecycle events (QUEUED, RUNNING, RESULT_UPDATE, COMPLETED) will be
         * streamed over SSE.
         */
        @PostMapping(value = "/submit", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public ResponseEntity<SseEmitter> submitCode(
                        @Valid @RequestBody CreateSubmissionRequest request,
                        @RequestHeader("x-user-id") String userId) {

                log.info("[SUBMISSION] Creating submission for user={}, problemId={}",
                                userId, request.getProblemId());
                
                // Service returns an SSE emitter already registered with
                // SubmissionStreamManager
                SubmissionWithEmitter result = submissionService.createSubmission(request, userId);

                log.info("SSE stream established for submissionId={}", result.getSubmissionId());

                return ResponseEntity.ok()
                                .contentType(MediaType.TEXT_EVENT_STREAM)
                                .header("X-Submission-Id", result.getSubmissionId())
                                .body(result.getEmitter());
        }

        @PostMapping(value= "/run",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public ResponseEntity<SseEmitter> dryRunCode(
                        @Valid @RequestBody CreateSubmissionRequest request,
                        @RequestHeader("x-user-id") String userId) {

                String executionId = UUID.randomUUID().toString();

         log.info("[DRY_RUN] Creating submission for user={}, problemId={}",
          userId, request.getProblemId());


          RunWithEmitter result = submissionService.createSubmissionDryRun(request, executionId ,userId);
          log.info("SSE stream established for executionId={}", result.getExecutionId());

                return ResponseEntity.ok()
                                .contentType(MediaType.TEXT_EVENT_STREAM)
                                .header("X-Execution-Id", result.getExecutionId())
                                .body(result.getEmitter());
        }

        @GetMapping("/result/{problemId}")
        public ResponseEntity<List<SubmissionResponse>> getSubmissionResult(@PathVariable String problemId ,@RequestHeader("x-user-id") String userId) {

                List<SubmissionResponse> result = submissionService.getSubmissionResultByProblemId(problemId,userId);
                return ResponseEntity.ok(result);
        }

        @GetMapping("/all-submissions")
        public ResponseEntity<List<SubmissionResponse>> getAllSubmissions(@RequestHeader("x-user-id") String userId) {

                List<SubmissionResponse> result = submissionService.getAllSubmissions(userId);
                return ResponseEntity.ok(result);
        }
}
