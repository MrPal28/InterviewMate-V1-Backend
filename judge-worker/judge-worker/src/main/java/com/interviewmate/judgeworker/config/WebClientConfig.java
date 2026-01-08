package com.interviewmate.judgeworker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {

  private final Judge0Properties props;

  public WebClientConfig(Judge0Properties props) {
    this.props = props;
  }

  @Bean
  public WebClient judge0WebClient() {
    HttpClient httpClient = HttpClient.create()
        .option(io.netty.channel.ChannelOption.SO_KEEPALIVE, true)
        .responseTimeout(Duration.ofMillis(props.getReadTimeoutMs()))
        .option(io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS, props.getConnectTimeoutMs());

    return WebClient.builder()
        .baseUrl(props.getBaseUrl())
        .clientConnector(new org.springframework.http.client.reactive.ReactorClientHttpConnector(httpClient))
        .exchangeStrategies(ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
            .build())
        .build();
  }
}
