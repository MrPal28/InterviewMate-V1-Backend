package com.interviewmate.codingservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.interviewmate.codingservice.entity.TestCase;

@Repository
public interface TestCaseRepository extends MongoRepository<TestCase, String> {

    List<TestCase> findByProblemId(String problemId);

    List<TestCase> findByProblemIdAndHiddenFalse(String problemId);

    void deleteAllByProblemId(String problemId);

    List<TestCase> findByProblemIdAndHidden(String problemId, boolean hidden);
}
