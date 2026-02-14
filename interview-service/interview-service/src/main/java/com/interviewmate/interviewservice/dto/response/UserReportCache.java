package com.interviewmate.interviewservice.dto.response;


import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@RedisHash("USER_REPORT")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReportCache {

    /**
     * Redis primary key.
     * Using sessionId is fine since each report is session-scoped.
     */
    @Id
    private String sessionId;

    /**
     * REQUIRED for findAllByUserId(...)
     */
    @Indexed
    private String userId;

    /**
     * Cached payload
     */
    private UserReportDto report;

    /**
     * TTL in seconds (e.g. 86400 = 24h)
     */
    @TimeToLive
    private Long ttl;

    /**
     * Factory method used by service layer
     */
    public static UserReportCache from(UserReportDto dto) {
        return UserReportCache.builder()
                .sessionId(dto.getSessionId())
                .userId(dto.getUserId())
                .report(dto)
                .ttl(86400L) // 24 hours
                .build();
    }
}
