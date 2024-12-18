package com.youragent.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        (request) ->
                                request
                                        .requestMatchers(
                                                "/locationFact",
                                                "/auth/createAgentAccount",
                                                "/auth/signin",
                                                "/processClientSubmit",
                                                "/locationInquiry"
                                                ).permitAll()
                                        .anyRequest().authenticated()
                        .anyRequest().authenticated()
                )
                .build();
    }

}
