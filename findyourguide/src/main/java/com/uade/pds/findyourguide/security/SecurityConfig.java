package com.uade.pds.findyourguide.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;

import java.security.Security;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain firstFilter(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests(auth ->
                    auth
                        .requestMatchers("/").permitAll()
                            .requestMatchers("/h2-console/**").permitAll()
        ).csrf(crsf -> crsf.disable())
                .headers(head -> head.frameOptions(frame -> frame.disable()));

        return httpSecurity.build();
    }
}
