package com.interviewmate.userservice.utils;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.interviewmate.userservice.config.CustomUserDetails;
import com.interviewmate.userservice.constants.Role;

@Component
public class AuthenticationFacadeImpl {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public CustomUserDetails getCurrentUserDetails() {
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return (CustomUserDetails) authentication.getPrincipal();
        }
        throw new IllegalStateException("No authenticated user found in context");
    }

    public UUID getCurrentUserId() {
        return getCurrentUserDetails().getId();
    }

    public String getCurrentUserEmail() {
        return getCurrentUserDetails().getUsername();
    }

    public Role getCurrentUserRole() {
        return getCurrentUserDetails().getRole();
    }
}
