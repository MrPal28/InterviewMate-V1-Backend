package com.interviewmate.userservice.exception;

import org.springframework.http.HttpStatus;

public class OtpExpiredException extends BaseException {

    public OtpExpiredException() {
        super("OTP expired or not found",
              "OTP_EXPIRED",
              HttpStatus.BAD_REQUEST);
    }
}
