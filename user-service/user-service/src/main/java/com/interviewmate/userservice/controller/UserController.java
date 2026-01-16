package com.interviewmate.userservice.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interviewmate.userservice.dto.UpdateUserRequest;
import com.interviewmate.userservice.dto.UserResponse;
import com.interviewmate.userservice.service.UserService;
import com.interviewmate.userservice.utils.AuthenticationFacadeImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final AuthenticationFacadeImpl authenticationFacadeImpl;


  @GetMapping("/profile")
  public ResponseEntity<UserResponse> getProfile(@RequestHeader("x-username") String email) {

    UserResponse response = userService.getUserByEmail(email.trim().toLowerCase());
    return ResponseEntity.ok(response);
  }

  @PutMapping("/update")
  public ResponseEntity<UserResponse> updateCurrentUserProfile(
      @Valid @RequestBody UpdateUserRequest request) {

    String email = authenticationFacadeImpl.getAuthentication().getName();
    // Fetch the current logged-in user from the security context
    UserResponse response = userService.updateUserProfile(email, request);

    return ResponseEntity.ok(response);
  }
}
