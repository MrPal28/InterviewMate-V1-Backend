package com.interviewmate.codingservice.service;

import java.util.List;

import com.interviewmate.codingservice.dto.CreateSubmissionRequest;
import com.interviewmate.codingservice.dto.RunWithEmitter;
import com.interviewmate.codingservice.dto.SubmissionResponse;
import com.interviewmate.codingservice.dto.SubmissionWithEmitter;
import com.interviewmate.codingservice.entity.Submission;

public interface SubmissionService {
  public SubmissionWithEmitter createSubmission(CreateSubmissionRequest submissionRequest, String userId);

  RunWithEmitter createSubmissionDryRun(CreateSubmissionRequest submissionRequest, String executionId,  String userId);

  Submission getSubmissionById(String id);

  List<Submission> getSubmissionsForUser(String userId);

  List<Submission> getSubmissionsForProblemAndUser(String problemId, String userId);

 List<SubmissionResponse> getSubmissionResultByProblemId(String problemId, String userId);

  List<SubmissionResponse> getAllSubmissions(String userId);
}
