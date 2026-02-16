package com.interviewmate.userservice.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends BaseException {

    public InvalidCredentialsException() {
        super("Invalid email or password",
              "INVALID_CREDENTIALS",
              HttpStatus.UNAUTHORIZED);
    }
}
