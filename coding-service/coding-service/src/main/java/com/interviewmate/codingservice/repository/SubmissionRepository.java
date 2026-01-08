package com.interviewmate.codingservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.interviewmate.codingservice.entity.Submission;

@Repository
public interface SubmissionRepository extends MongoRepository<Submission, String> {

    List<Submission> findByUserId(String userId);

    List<Submission> findByProblemIdAndUserId(String problemId, String userId);

}
