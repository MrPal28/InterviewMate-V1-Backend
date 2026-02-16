package com.interviewmate.userservice.exception;

import org.springframework.http.HttpStatus;

public class InvalidOtpException extends BaseException {

    public InvalidOtpException() {
        super("Invalid OTP provided",
              "INVALID_OTP",
              HttpStatus.BAD_REQUEST);
    }
}

