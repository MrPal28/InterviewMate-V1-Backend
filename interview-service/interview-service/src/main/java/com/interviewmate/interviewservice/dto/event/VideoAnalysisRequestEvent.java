package com.interviewmate.interviewservice.dto.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoAnalysisRequestEvent {

    private String userid;
    private String sessionid;
    private int questionno;
    private String question;
    private String videourl;
    private int totalnumberofquestion;
}
