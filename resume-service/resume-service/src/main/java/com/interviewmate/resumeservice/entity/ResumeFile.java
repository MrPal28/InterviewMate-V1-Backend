package com.interviewmate.resumeservice.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;



@Entity
@Table(name = "resume_file")
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ResumeFile {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID fileid;

  private String userId;
  private String filePublicId;
  private String fileUrl;
  @Lob
  @Column(columnDefinition = "LONGTEXT")
  private String jobDescription;
  private LocalDateTime uploadedAt;
}
