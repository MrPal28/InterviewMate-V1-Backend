package com.interviewmate.interviewservice.repository.mongorepo;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.interviewmate.interviewservice.entity.UserReportDocument;

import reactor.core.publisher.Flux;

@Repository
public interface UserReportMongoRepository
        extends ReactiveMongoRepository<UserReportDocument, String> {
                
    Flux<UserReportDocument> findAllByUserId(String userId);
}
