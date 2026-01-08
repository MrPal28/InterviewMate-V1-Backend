package com.interviewmate.userservice.config;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.interviewmate.userservice.model.User;
import com.interviewmate.userservice.repository.UserRepository;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = repository.findByEmail(username);

        User user = userOpt.orElseThrow(() -> {
            log.warn("User not found with email: {}", username);
            return new UsernameNotFoundException("User not found with email: " + username);
        });

        log.info("User loaded successfully with email: {}", username);
        return new CustomUserDetails(user);
    }
}
