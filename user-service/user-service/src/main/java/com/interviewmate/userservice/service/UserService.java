package com.interviewmate.userservice.service;




import com.interviewmate.userservice.dto.UpdateUserRequest;
import com.interviewmate.userservice.dto.UserResponse;


public interface UserService {
  // UserResponse register(RegisterRequestDTO request);
  // LoginResponse login(LoginRequestDTO request);
  // UserResponse getCurrentUser();
  UserResponse updateUserProfile(String email , UpdateUserRequest request);
  // boolean resetPassword(String email, String otp, String newPassword);
  UserResponse getUserByEmail(String email);
}
