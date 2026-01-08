package com.interviewmate.resumeservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interviewmate.resumeservice.entity.ResumeFile;


@Repository
public interface ResumeFileRepository extends JpaRepository<ResumeFile,UUID>{
  
}
