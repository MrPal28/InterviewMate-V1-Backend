package com.interviewmate.interviewservice.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class PythonInitInterviewResponse {
  
    private String userid;
    private String sessionid;
    private List<String> slotonequestions;
    private List<String> slottwoquestions;
    private int remanning;
    private int numberofquestions;
}
