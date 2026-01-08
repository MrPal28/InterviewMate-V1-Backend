package com.interviewmate.apigateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtConfig {
  private String secret;
}
