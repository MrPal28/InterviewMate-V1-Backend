package com.interviewmate.userservice.oauth2;


// import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviewmate.userservice.config.CustomUserDetails;
// import com.interviewmate.userservice.dto.LoginResponse;
import com.interviewmate.userservice.model.User;
import com.interviewmate.userservice.repository.UserRepository;
import com.interviewmate.userservice.utils.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Value("${oauth.success.redirect}")
    private String successRedirect;

    @Value("${oauth.login.failed.redirect}")
    private String failedRedirect;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Authentication authentication)
        throws IOException {

    OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

    String email = oauthUser.getAttribute("email");

    User user = userRepository.findByEmail(email).orElse(null);
    if(user == null){
        response.sendRedirect(failedRedirect);
        return;
    }

    // Create CustomUserDetails directly instead of loading via UserDetailsService
    CustomUserDetails userDetails = new CustomUserDetails(user);

    String token = jwtService.generateToken(userDetails);

    // LoginResponse loginResponse = LoginResponse.builder()
    //         .id(user.getId())
    //         .email(user.getEmail())
    //         .role(user.getRole())
    //         .token(token)
    //         .build();


    // response.setStatus(HttpServletResponse.SC_OK);
    // response.setContentType("application/json");
    // response.getWriter().write(new ObjectMapper().writeValueAsString(loginResponse));
    // response.getWriter().flush();

    String redirectUrl = successRedirect + token;

    response.sendRedirect(redirectUrl);
}
}