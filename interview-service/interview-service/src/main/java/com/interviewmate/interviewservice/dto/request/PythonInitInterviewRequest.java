package com.interviewmate.interviewservice.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PythonInitInterviewRequest {

    private String userid;
    private String resumeurl;
    private boolean specificquestionrequirement;
    private List<String> subjectortopic;
    private int numberofquestions;
    private String level;
}
