package com.interviewmate.interviewservice.api.v1.controller;


import com.interviewmate.interviewservice.orchestration.InterviewOrchestrator;
import com.interviewmate.interviewservice.dto.request.SecondSlotRequest;
import com.interviewmate.interviewservice.dto.request.StartInterviewRequest;
import com.interviewmate.interviewservice.dto.request.SubmitAnswerRequest;
import com.interviewmate.interviewservice.dto.response.StartInterviewResponse;
import com.interviewmate.interviewservice.dto.response.UserReportDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewOrchestrator interviewOrchestrator;

    @PostMapping("/start-interview")
    public Mono<StartInterviewResponse> startInterview(
            @Valid @RequestBody StartInterviewRequest request, @RequestHeader("x-user-id") String userId) {
        return interviewOrchestrator.startInterview(request, userId);
    }

    @PostMapping("/next-slot")
    public Mono<StartInterviewResponse> fetchSecondSlot(
            @Valid @RequestBody SecondSlotRequest request , @RequestHeader("x-user-id") String userId) {
        return interviewOrchestrator.fetchSecondSlot(request, userId);
    }

    @PostMapping("/submit-answer")
    public Mono<Void> submitAnswer(@RequestBody SubmitAnswerRequest request , @RequestHeader("x-user-id") String userId) {
        return interviewOrchestrator.submitAnswer(request, userId);
    }

    @GetMapping("/get-user-report")
    public Mono<List<UserReportDto>> getUserReport(@RequestHeader("x-user-id") String userId) {
       
            return interviewOrchestrator.getUserReport(userId);
     
    }

} 
