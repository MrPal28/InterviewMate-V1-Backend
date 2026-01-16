package com.interviewmate.userservice.config;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.interviewmate.userservice.constants.Role;
import com.interviewmate.userservice.model.User;

import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    // Prefix "ROLE_" is standard for Spring Security
    return java.util.Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
    );
}


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public UUID getId() {
        return user.getId();  // assuming it's Long in entity
    }

    public Role getRole() {
        return user.getRole();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // can be tied to DB field later
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // can be tied to DB field later
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // can be tied to DB field later
    }

    @Override
    public boolean isEnabled() {
        return true; // can be tied to DB field later
    }
}
