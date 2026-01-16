package com.interviewmate.resumeservice.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interviewmate.resumeservice.entity.Resume;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, UUID> {

  Optional<Resume> findByUserId(UUID userId);
  
}
