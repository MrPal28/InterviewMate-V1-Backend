package com.interviewmate.userservice.oauth2;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request)
            throws OAuth2AuthenticationException {

        OAuth2User oauthUser = super.loadUser(request);

        Map<String, Object> attributes = oauthUser.getAttributes();
        String registrationId =
                request.getClientRegistration().getRegistrationId();

        String email;
        String name;

        if ("google".equals(registrationId)) {
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
        } else if ("github".equals(registrationId)) {
            email = (String) attributes.get("email");
            name = (String) attributes.get("login");

            // GitHub sometimes doesn't return email
            if (email == null) {
                throw new OAuth2AuthenticationException(
                        new OAuth2Error("email_not_found"),
                        "Email not found from GitHub provider"
                );
            }
        } else {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("unsupported_provider"),
                    "Login with this provider is not supported"
            );
        }

        Map<String, Object> mappedAttributes = new HashMap<>();
        mappedAttributes.put("email", email);
        mappedAttributes.put("name", name);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                mappedAttributes,
                "email"
        );
    }
}