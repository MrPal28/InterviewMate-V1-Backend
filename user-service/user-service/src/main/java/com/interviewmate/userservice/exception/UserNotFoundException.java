package com.interviewmate.userservice.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String email) {
        super("User not found with email: " + email,
              "USER_NOT_FOUND",
              HttpStatus.NOT_FOUND);
    }
}
