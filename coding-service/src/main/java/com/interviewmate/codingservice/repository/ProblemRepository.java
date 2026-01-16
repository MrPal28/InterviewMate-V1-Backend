package com.interviewmate.codingservice.repository;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.interviewmate.codingservice.entity.Problem;

@Repository
public interface ProblemRepository extends MongoRepository<Problem, String> {

    Page<Problem> findByTitleContainingIgnoreCaseAndDifficulty(String title, String difficulty, Pageable pageable);

    Page<Problem> findByTitleContainingIgnoreCaseAndTagsContaining(String title, String tag, Pageable pageable);

    Page<Problem> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Problem> findByDifficulty(String difficulty, Pageable pageable);

    Page<Problem> findByTagsContaining(String tag, Pageable pageable);

    boolean existsBySlug(String slug);

}
