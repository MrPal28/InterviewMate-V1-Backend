package com.interviewmate.interviewservice.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PythonSecondSlotRequest {

    private String userid;
    private String sessionid;
    private int remanning;
    private String level;
}
