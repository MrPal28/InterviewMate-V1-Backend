package com.interviewmate.userservice.exception;

import org.springframework.http.HttpStatus;

public class RedisUnavailableException extends BaseException {

    public RedisUnavailableException() {
        super("OTP service temporarily unavailable",
              "REDIS_UNAVAILABLE",
              HttpStatus.SERVICE_UNAVAILABLE);
    }
}

