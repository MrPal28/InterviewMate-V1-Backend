package com.interviewmate.codingservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.interviewmate.codingservice.entity.CodeTemplate;

@Repository
public interface CodeTemplateRepository extends MongoRepository<CodeTemplate, String> {

  List<CodeTemplate> findByProblemId(String problemId);

  void deleteAllByProblemId(String problemId);
  
}
