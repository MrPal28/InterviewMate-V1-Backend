package com.interviewmate.resumeservice.service.implementation;

import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interviewmate.resumeservice.dto.ResumeRequest;
import com.interviewmate.resumeservice.dto.ResumeResponseDTO;
import com.interviewmate.resumeservice.entity.Resume;
import com.interviewmate.resumeservice.repository.ResumeRepository;
import com.interviewmate.resumeservice.service.ResumeBuilder;
import com.interviewmate.resumeservice.util.ResumeMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@RequiredArgsConstructor
@Service
@Slf4j
public class ResumeBuilderImpl implements ResumeBuilder {

        private final ResumeRepository resumeRepository;

        @Override
        @Transactional
        @CachePut(value = "resumeCache", key = "#userId") //  update cache after save
        public ResumeResponseDTO saveOrUpdateResume(UUID userId, ResumeRequest request) {
                log.info("Saving/updating resume for userId={}", userId);

                Resume existingResume = resumeRepository.findByUserId(userId)
                                .orElseGet(() -> Resume.builder().userId(userId).build());

                Resume updatedResume = ResumeMapper.toEntity(request);
                updatedResume.setId(existingResume.getId());
                updatedResume.setUserId(userId);

                Optional.ofNullable(updatedResume.getExperience())
                                .ifPresent(exps -> exps.forEach(e -> e.setResume(updatedResume)));
                Optional.ofNullable(updatedResume.getEducation())
                                .ifPresent(edus -> edus.forEach(e -> e.setResume(updatedResume)));
                Optional.ofNullable(updatedResume.getSkills())
                                .ifPresent(skills -> skills.forEach(s -> s.setResume(updatedResume)));
                Optional.ofNullable(updatedResume.getProjects())
                                .ifPresent(projects -> projects.forEach(p -> p.setResume(updatedResume)));

                Resume saved = resumeRepository.save(updatedResume);
                log.info("Resume saved successfully for userId={}", userId);

                return ResumeMapper.toDTO(saved);
        }

        @Override
        @Transactional(readOnly = true)
        @Cacheable(value = "resumeCache", key = "#userId") //  check cache first
        public ResumeResponseDTO getResume(UUID userId) {
                log.info("Fetching resume for userId={} from DB (cache miss)", userId);
                Resume resume = resumeRepository.findByUserId(userId)
                                .orElseThrow(() -> new RuntimeException("Resume not found"));
                return ResumeMapper.toDTO(resume);
        }

}
