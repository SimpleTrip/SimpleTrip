package com.ssafy.trip.jwt.security;

import com.ssafy.trip.jwt.JwtFilter;
import com.ssafy.trip.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
//    private final SecurityService service;
    private final JwtFilter filter;
    private final JwtProvider provide;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().anyRequest();
//        return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/");
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable().headers().frameOptions().disable();
        http.formLogin().disable().logout().disable();
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable()
            .cors(Customizer.withDefaults())
            .authorizeRequests()
            .anyRequest().permitAll();
        return http.build();
    }
}