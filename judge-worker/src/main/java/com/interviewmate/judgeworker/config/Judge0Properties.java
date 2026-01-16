package com.interviewmate.judgeworker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "coding.judge0")
public class Judge0Properties {
    private String baseUrl;
    private long pollIntervalMs ;
    private int maxPollAttempts ;
    private int connectTimeoutMs ;
    private int readTimeoutMs ;

    // getters & setters
    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
    public long getPollIntervalMs() { return pollIntervalMs; }
    public void setPollIntervalMs(long pollIntervalMs) { this.pollIntervalMs = pollIntervalMs; }
    public int getMaxPollAttempts() { return maxPollAttempts; }
    public void setMaxPollAttempts(int maxPollAttempts) { this.maxPollAttempts = maxPollAttempts; }
    public int getConnectTimeoutMs() { return connectTimeoutMs; }
    public void setConnectTimeoutMs(int connectTimeoutMs) { this.connectTimeoutMs = connectTimeoutMs; }
    public int getReadTimeoutMs() { return readTimeoutMs; }
    public void setReadTimeoutMs(int readTimeoutMs) { this.readTimeoutMs = readTimeoutMs; }
}