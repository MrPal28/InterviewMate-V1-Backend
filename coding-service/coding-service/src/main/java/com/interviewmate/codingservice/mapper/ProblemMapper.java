package com.interviewmate.codingservice.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.interviewmate.codingservice.dto.problemDto.CodeTemplateResponse;
import com.interviewmate.codingservice.dto.problemDto.CreateCodeTemplateRequest;
import com.interviewmate.codingservice.dto.problemDto.CreateProblemRequest;
import com.interviewmate.codingservice.dto.problemDto.ProblemResponse;
import com.interviewmate.codingservice.dto.problemDto.SampleIODto;
import com.interviewmate.codingservice.dto.problemDto.UpdateProblemRequest;
import com.interviewmate.codingservice.entity.CodeTemplate;
import com.interviewmate.codingservice.entity.Problem;
import com.interviewmate.codingservice.entity.SampleIO;

@Component
public class ProblemMapper {

        public Problem toEntity(CreateProblemRequest dto) {
                return Problem.builder()
                                .title(dto.getTitle())
                                .slug(dto.getSlug())
                                .description(dto.getDescription())
                                .difficulty(dto.getDifficulty())
                                .tags(dto.getTags())
                                .sample(dto.getSample() == null ? null
                                                : dto.getSample().stream()
                                                                .map(this::toSampleEntity)
                                                                .collect(Collectors.toList()))
                                .companies(dto.getCompanies())
                                .premium(dto.isPremium())
                                .build();
        }

        public ProblemResponse toDto(Problem problem, List<CodeTemplateResponse> codeTemplatesResponse) {
                return ProblemResponse.builder()
                                .id(problem.getId())
                                .title(problem.getTitle())
                                .slug(problem.getSlug())
                                .description(problem.getDescription())
                                .difficulty(problem.getDifficulty())
                                .tags(problem.getTags())
                                .sample(problem.getSample() == null ? null
                                                : problem.getSample().stream()
                                                                .map(this::toSampleDto)
                                                                .collect(Collectors.toList()))
                                .codeTemplates(codeTemplatesResponse)
                                .companies(problem.getCompanies())
                                .premium(problem.isPremium())

                                .build();
        }

        public void updateEntityFromDto(Problem problem, UpdateProblemRequest dto) {
                 if (dto.getTitle() != null)
                        problem.setTitle(dto.getTitle());

                if (dto.getDescription() != null)
                        problem.setDescription(dto.getDescription());

                if (dto.getDifficulty() != null)
                        problem.setDifficulty(dto.getDifficulty());

                if (dto.getPremium() != null)
                        problem.setPremium(dto.getPremium());

                if (dto.getTags() != null)
                        problem.setTags(dto.getTags());

                if (dto.getCompanies() != null)
                        problem.setCompanies(dto.getCompanies());

                if (dto.getSample() != null) {
                        problem.setSample(
                                        dto.getSample().stream()
                                                        .map(this::toSampleEntity)
                                                        .toList());
                }

        }

        public CodeTemplateResponse toCodeTemplateDto(CodeTemplate codeTemplate) {
                return CodeTemplateResponse.builder()
                                .languageId(codeTemplate.getLanguageId())
                                .template(codeTemplate.getTemplate())
                                .build();
        }

        private SampleIO toSampleEntity(SampleIODto dto) {
                return SampleIO.builder()
                                .stdin(dto.getStdin())
                                .stdout(dto.getStdout())
                                .explanation(dto.getExplanation())
                                .build();
        }

        private SampleIODto toSampleDto(SampleIO sample) {
                return SampleIODto.builder()
                                .stdin(sample.getStdin())
                                .stdout(sample.getStdout())
                                .explanation(sample.getExplanation())
                                .build();
        }

        public CodeTemplate toTemplateEntity(
                        CreateCodeTemplateRequest dto,
                        String problemId) {
                return CodeTemplate.builder()
                                .problemId(problemId)
                                .languageId(dto.getLanguage())
                                .template(dto.getTemplate())
                                .build();
        }

}
