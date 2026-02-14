package com.interviewmate.interviewservice.repository.redisrepo;

import com.interviewmate.interviewservice.entity.InterviewSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewSessionRepository
        extends CrudRepository<InterviewSession, String> {
}
