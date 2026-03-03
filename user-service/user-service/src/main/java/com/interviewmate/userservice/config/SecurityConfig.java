package com.interviewmate.userservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.interviewmate.userservice.filter.JwtAuthenticationFilter;
import com.interviewmate.userservice.oauth2.CustomOAuth2UserService;
import com.interviewmate.userservice.oauth2.OAuth2SuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
            OAuth2SuccessHandler oAuth2SuccessHandler,
            CustomOAuth2UserService customOAuth2UserService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/register",
                                "/api/v1/auth/login",
                                "/api/v1/auth/getotp",
                                "/api/v1/auth/verifyotp",
                                "/api/v1/auth/forgot-password",
                                "/api/v1/auth/reset-password",
                                "/oauth2/**",
                                "/login/oauth2/**"

                        ).permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .successHandler(oAuth2SuccessHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // @Bean
    // public AuthenticationManager authenticationManager() {
    //     DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
    //     authProvider.setPasswordEncoder(passwordEncoder());
    //     return new ProviderManager(authProvider);
    // }

}
