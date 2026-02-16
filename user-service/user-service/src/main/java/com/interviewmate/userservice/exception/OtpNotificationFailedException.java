package com.interviewmate.userservice.exception;

import org.springframework.http.HttpStatus;

public class OtpNotificationFailedException extends BaseException {

    public OtpNotificationFailedException() {
        super("Failed to send OTP notification",
              "OTP_NOTIFICATION_FAILED",
              HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

