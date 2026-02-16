package com.interviewmate.userservice.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BaseException {

    public UserAlreadyExistsException(String email) {
        super("User already exists with email: " + email,
              "USER_ALREADY_EXISTS",
              HttpStatus.CONFLICT);
    }
}
