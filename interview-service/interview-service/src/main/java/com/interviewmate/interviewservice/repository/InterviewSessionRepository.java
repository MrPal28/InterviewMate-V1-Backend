package com.interviewmate.interviewservice.repository;

import com.interviewmate.interviewservice.entity.InterviewSession;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewSessionRepository
        extends ReactiveCrudRepository<InterviewSession, String> {
}
