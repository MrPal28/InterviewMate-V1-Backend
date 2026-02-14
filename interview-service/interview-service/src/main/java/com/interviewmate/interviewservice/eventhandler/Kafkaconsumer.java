package com.interviewmate.interviewservice.eventhandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviewmate.interviewservice.dto.response.QuestionAnswerEvaluationDto;
import com.interviewmate.interviewservice.dto.response.UserReportCache;
import com.interviewmate.interviewservice.dto.response.UserReportDto;
import com.interviewmate.interviewservice.entity.UserReportDocument;
import com.interviewmate.interviewservice.repository.mongorepo.UserReportMongoRepository;
import com.interviewmate.interviewservice.repository.redisrepo.UserReportRedisRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Component
@RequiredArgsConstructor
public class Kafkaconsumer {

  private final UserReportMongoRepository userReportMongoRepository;
  private final UserReportRedisRepository userReportRedisRepository;
  private final ObjectMapper objectMapper;

  @KafkaListener(topics = "UserReport", groupId = "interview-service")
  public void consumeUserReport(String message) {

    log.info(" Kafka message received");
    log.debug(" Raw payload={}", message);

    JsonNode root;
    try {
      root = objectMapper.readTree(message);
    } catch (Exception e) {
      log.error(" Invalid JSON received from Kafka", e);
      return;
    }

    UserReportDto report = new UserReportDto();

    /* ---------------- BASIC FIELDS (LOWERCASE – PYTHON CONTRACT) ---------------- */

    report.setSessionId(root.path("sessionid").asText(null));
    report.setUserId(root.path("userid").asText(null));
    report.setOverallScore(root.path("overallscore").asInt(0));
    report.setBehavioralImprovement(
        root.path("behavioralimprovement").asText(null)
    );
    report.setImprovementSuggestion(
        root.path("improvementsuggestion").asText(null)
    );

    /* ---------------- QUESTION & ANSWER LIST ---------------- */

    List<QuestionAnswerEvaluationDto> qaList = new ArrayList<>();
    JsonNode qaNode = root.path("Questions"); 

    if (qaNode.isArray()) {
      for (JsonNode item : qaNode) {

        QuestionAnswerEvaluationDto qa = new QuestionAnswerEvaluationDto();

        qa.setQuestionNo(item.path("questionno").asInt());
        qa.setQuestion(item.path("question").asText(null));
        qa.setAnswer(item.path("answer").asText(null));
        qa.setActualQuestionAnswer(
            item.path("actualquestionanswer").asText(null));

        qaList.add(qa);
      }
    } else {
      log.warn("'Questions' node missing or not an array");
    }

    report.setQuestionAndAnswer(qaList);

    /* ---------------- VALIDATION ---------------- */

    if (report.getSessionId() == null || report.getUserId() == null) {
      log.error(" Mandatory fields missing. sessionId or userId is null");
      log.error(" Raw payload={}", message);
      return;
    }

    /* ---------------- LOGGING ---------------- */

    log.info(" Parsed UserReportDto:");
    log.info("   sessionId={}", report.getSessionId());
    log.info("   userId={}", report.getUserId());
    log.info("   overallScore={}", report.getOverallScore());
    log.info("   behavioralImprovement={}", report.getBehavioralImprovement());
    log.info("   improvementSuggestion={}", report.getImprovementSuggestion());
    log.info("   questionAndAnswerCount={}",
        report.getQuestionAndAnswer() != null ? report.getQuestionAndAnswer().size() : 0);

    Instant now = Instant.now();

    /* ---------------- MONGO SAVE ---------------- */

    UserReportDocument document = UserReportDocument.builder()
        .sessionId(report.getSessionId())
        .userId(report.getUserId())
        .questionAndAnswer(report.getQuestionAndAnswer())
        .behavioralImprovement(report.getBehavioralImprovement())
        .improvementSuggestion(report.getImprovementSuggestion())
        .overallScore(report.getOverallScore())
        .createdAt(now)
        .build();

    log.info(" Saving UserReportDocument to MongoDB");

    userReportMongoRepository.save(document)
        .doOnSuccess(saved ->
            log.info(" MongoDB save successful | sessionId={}", report.getSessionId()))
        .doOnError(err ->
            log.error(" MongoDB save failed | sessionId={}", report.getSessionId(), err))
        .subscribe();

    /* ---------------- REDIS CACHE ---------------- */

    UserReportCache cache = UserReportCache.builder()
        .sessionId(report.getSessionId())
        .userId(report.getUserId())
        .report(report)
        .ttl(86400L) // 24 hours
        .build();

    log.info(" Saving UserReport to Redis | key=USER_REPORT:{}", report.getSessionId());

    Mono.fromCallable(() -> userReportRedisRepository.save(cache))
        .subscribeOn(Schedulers.boundedElastic())
        .doOnSuccess(r ->
            log.info(" Redis save successful | sessionId={}", report.getSessionId()))
        .doOnError(err ->
            log.error(" Redis save failed | sessionId={}", report.getSessionId(), err))
        .subscribe();

    log.info(" Kafka consumer processing completed for sessionId={}", report.getSessionId());
  }
}
