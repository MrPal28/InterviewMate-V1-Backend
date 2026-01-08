package com.interviewmate.judgeworker.consumercontroller;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.interviewmate.judgeworker.Judge0.Judge0Client;
import com.interviewmate.judgeworker.dto.EvaluationResult;
import com.interviewmate.judgeworker.dto.JudgeTestCaseDto;
import com.interviewmate.judgeworker.dto.SubmissionExecutionContext;
import com.interviewmate.judgeworker.dto.TestCaseResult;
import com.interviewmate.judgeworker.evaluator.StdoutEvaluator;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubmissionCreatedConsumer {

    private final Judge0Client judge0Client;
    private final KafkaTemplate<String, Object> kafka;
    private final StdoutEvaluator stdoutEvaluator;

    @Value("${coding.judge0.pollIntervalMs}")
    private long pollIntervalMs;

    @Value("${coding.judge0.maxPollAttempts}")
    private int maxAttempts;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    private final Map<String, SubmissionExecutionContext> executions = new ConcurrentHashMap<>();

    // ==================================================
    // EVENT: submissions.created
    // ==================================================
    @KafkaListener(topics = "submissions.created", groupId = "judge-workers")
    public void onCreated(Map<String, Object> payload) {

        log.info("[EVENT] submissions.created payloadKeys={}", payload.keySet());

        String submissionId = (String) payload.get("submissionId");
        String sourceCode = (String) payload.get("sourceCode");
        Integer languageId = (Integer) payload.get("languageId");
        String traceId = (String) payload.get("traceId");

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> rawTestCases = (List<Map<String, Object>>) payload.get("testCases");

        if (submissionId == null || sourceCode == null || languageId == null || rawTestCases == null) {
            log.error("[INVALID EVENT] payload={}", payload);
            return;
        }

        // 🔹 Map + filter invalid test cases
        List<JudgeTestCaseDto> testCases = rawTestCases.stream()
                .map(tc -> JudgeTestCaseDto.builder()
                        .stdin((String) tc.get("stdin"))
                        .stdout((String) tc.get("stdout"))
                        .hidden(Boolean.TRUE.equals(tc.get("hidden")))
                        .build())
                .filter(tc -> tc.getStdin() != null && !tc.getStdin().isBlank())
                .filter(tc -> tc.getStdout() != null && !tc.getStdout().isBlank())
                .toList();

        if (testCases.isEmpty()) {
            log.error("[NO VALID TESTCASES] submissionId={}", submissionId);
            return;
        }

        SubmissionExecutionContext ctx = new SubmissionExecutionContext();
        ctx.setSubmissionId(submissionId);
        ctx.setSourceCode(sourceCode);
        ctx.setLanguageId(languageId);
        ctx.setTraceId(traceId);
        ctx.setTestCases(testCases);

        executions.put(submissionId, ctx);

        log.info(
                "[SUBMISSION STARTED] submissionId={} totalTestCases={}",
                submissionId, testCases.size());

        executeNextTestCase(ctx);
    }

    // ==================================================
    // EXECUTE ONE TEST CASE
    // ==================================================
    private void executeNextTestCase(SubmissionExecutionContext ctx) {

        if (!ctx.hasNext()) {
            finalizeSubmission(ctx);
            return;
        }

        JudgeTestCaseDto tc = ctx.currentTestCase();
        int index = ctx.getCurrentIndex();

        log.info(
                "[TESTCASE START] submissionId={} index={}",
                ctx.getSubmissionId(), index);

        Map<String, Object> body = new HashMap<>();
        body.put("source_code", ctx.getSourceCode());
        body.put("language_id", ctx.getLanguageId());
        body.put("stdin", tc.getStdin());

        judge0Client.submit(body)
                .subscribe(token -> {
                    log.info(
                            "[JUDGE0 TOKEN] submissionId={} index={} token={}",
                            ctx.getSubmissionId(), index, token);
                    pollSingleTestCase(ctx, token, 0);
                }, err -> {
                    log.error(
                            "[JUDGE0 SUBMIT FAILED] submissionId={} error={}",
                            ctx.getSubmissionId(), err.getMessage(), err);
                    failSubmission(ctx, "JUDGE0_SUBMIT_FAILED");
                });
    }

    // ==================================================
    // POLL ONE TEST CASE
    // ==================================================
    private void pollSingleTestCase(
            SubmissionExecutionContext ctx,
            String token,
            int attempt) {

        if (attempt >= maxAttempts) {
            failSubmission(ctx, "MAX_POLL_ATTEMPTS");
            return;
        }

        scheduler.schedule(() -> {

            judge0Client.getSubmissionResult(token)
                    .subscribe(result -> {

                        @SuppressWarnings("unchecked")
                        Map<String, Object> status = (Map<String, Object>) result.get("status");
                        int statusId = Integer.parseInt(status.get("id").toString());

                        int index = ctx.getCurrentIndex();

                        log.info(
                                "[POLL] submissionId={} index={} attempt={} statusId={}",
                                ctx.getSubmissionId(), index, attempt, statusId);

                        if (statusId < 3) {
                            pollSingleTestCase(ctx, token, attempt + 1);
                            return;
                        }

                        String stdout = Objects.toString(result.get("stdout"), "");
                        JudgeTestCaseDto tc = ctx.currentTestCase();

                        TestCaseResult tcr = stdoutEvaluator.evaluateSingle(index, stdout, tc);

                        ctx.getResults().add(tcr);

                        log.info(
                                "[TESTCASE DONE] submissionId={} index={} passed={}",
                                ctx.getSubmissionId(), index, tcr.isPassed());

                        boolean isLast = index == ctx.getTestCases().size() - 1;

                        if (isLast) {
                            finalizeSubmission(ctx);
                            return;
                        }

                        ctx.moveNext();
                        executeNextTestCase(ctx);

                    }, err -> pollSingleTestCase(ctx, token, attempt + 1));

        }, pollIntervalMs, TimeUnit.MILLISECONDS);
    }

    // ==================================================
    // FINALIZE
    // ==================================================
    private void finalizeSubmission(SubmissionExecutionContext ctx) {

        if (!ctx.markFinalized()) {
            return; // already finalized
        }

        EvaluationResult result = EvaluationResult.fromTestCaseResults(ctx.getResults());

        Map<String, Object> completedEvent = new HashMap<>();
        completedEvent.put("submissionId", ctx.getSubmissionId());
        completedEvent.put("verdict", result.getVerdict());
        completedEvent.put("results", ctx.getResults());
        completedEvent.put("traceId", ctx.getTraceId());
        completedEvent.put("finishedAt", Instant.now().toString());

        kafka.send(
                "submissions.completed",
                ctx.getSubmissionId(),
                completedEvent);

        executions.remove(ctx.getSubmissionId());

        log.info(
                "[SUBMISSION COMPLETED] submissionId={} verdict={}",
                ctx.getSubmissionId(), result.getVerdict());
    }

    // ==================================================
    // FAILURE
    // ==================================================
    private void failSubmission(SubmissionExecutionContext ctx, String cause) {

        if (!ctx.markFinalized()) {
            return;
        }

        Map<String, Object> failedEvent = new HashMap<>();
        failedEvent.put("submissionId", ctx.getSubmissionId());
        failedEvent.put("error", cause);
        failedEvent.put("traceId", ctx.getTraceId());
        failedEvent.put("timestamp", Instant.now().toString());

        kafka.send(
                "submissions.failed",
                ctx.getSubmissionId(),
                failedEvent);

        executions.remove(ctx.getSubmissionId());

        log.error(
                "[SUBMISSION FAILED] submissionId={} cause={}",
                ctx.getSubmissionId(), cause);
    }
}
