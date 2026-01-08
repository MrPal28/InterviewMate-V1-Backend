package com.interviewmate.codingservice.EventController;

import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.interviewmate.codingservice.config.SubmissionStreamManager;
import com.interviewmate.codingservice.constants.SubmissionStatus;
import com.interviewmate.codingservice.repository.SubmissionRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SubmissionResultUpdateConsumer {

    private final SubmissionStreamManager streamManager;
    private final SubmissionRepository submissionRepository;

    public SubmissionResultUpdateConsumer(SubmissionStreamManager streamManager, SubmissionRepository submissionRepository) {
        this.streamManager = streamManager;
        this.submissionRepository = submissionRepository;
    }

    // RUNNING event
    @KafkaListener(topics = "submissions.running", groupId = "coding-service")
    public void onRunning(Map<String,Object> event) {
        String submissionId = (String) event.get("submissionId");
        streamManager.send(submissionId, "RUNNING", event);
    }

    //COMPLETED event
    @KafkaListener(topics = "submissions.completed", groupId = "coding-service")
    public void onCompleted(Map<String,Object> event) {
        String submissionId = (String) event.get("submissionId");

        streamManager.send(submissionId, "COMPLETED", event);
        log.info("Submission completed: {}", submissionId , event);
        submissionRepository.findById(submissionId).ifPresent(submission -> {
            submission.setStatus(SubmissionStatus.SUCCESS);
            submissionRepository.save(submission);
        });
        // Final step: close SSE
        streamManager.complete(submissionId);
     
    }

    // FAILED
    @KafkaListener(topics = "submissions.failed", groupId = "coding-service")
    public void onFailure(Map<String,Object> event) {
        String submissionId = (String) event.get("submissionId");
        streamManager.send(submissionId, "FAILED", event);
        log.info("Submission failed: {}", submissionId , event);
        submissionRepository.findById(submissionId).ifPresent(submission -> {
            submission.setStatus(SubmissionStatus.FAILURE);
            submissionRepository.save(submission);
        });
        // Final step: close SSE
        streamManager.complete(submissionId);
    }
}
