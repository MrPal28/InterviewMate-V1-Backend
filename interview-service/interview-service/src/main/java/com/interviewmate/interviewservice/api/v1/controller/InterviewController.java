package com.interviewmate.interviewservice.api.v1.controller;


import com.interviewmate.interviewservice.orchestration.InterviewOrchestrator;
import com.interviewmate.interviewservice.dto.request.SecondSlotRequest;
import com.interviewmate.interviewservice.dto.request.StartInterviewRequest;
import com.interviewmate.interviewservice.dto.response.StartInterviewResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewOrchestrator interviewOrchestrator;

    @PostMapping("/start")
    public Mono<StartInterviewResponse> startInterview(
            @Valid @RequestBody StartInterviewRequest request) {
        return interviewOrchestrator.startInterview(request);
    }

    @PostMapping("/next-slot")
    public Mono<StartInterviewResponse> fetchSecondSlot(
            @Valid @RequestBody SecondSlotRequest request) {
        return interviewOrchestrator.fetchSecondSlot(request);
    }

} 
