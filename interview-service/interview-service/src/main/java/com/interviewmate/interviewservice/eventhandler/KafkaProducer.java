package com.interviewmate.interviewservice.eventhandler;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.interviewmate.interviewservice.dto.event.VideoAnalysisRequestEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class KafkaProducer {
   private final KafkaTemplate<String, VideoAnalysisRequestEvent> kafkaTemplate;

    private static final String TOPIC = "video_analysis_request";

    public void publish(VideoAnalysisRequestEvent event) {
        kafkaTemplate.send(TOPIC, event.getSessionid(), event);
    }
}
