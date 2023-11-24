package com.ssafy.trip.jwt.security;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trip.jwt.JwtProvider;
import com.ssafy.trip.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {
    private final JwtProvider provider;
    private final ObjectMapper objectMapper;
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getJwtFrom(request);
        if (token != null && provider.checkToken(token)) {
            JwtSecurityUser user = provider.parseToken(token);
            SecurityContextHolder.getContext().setAuthentication(createAuthenticationToken(user));
        }
        filterChain.doFilter(request, response);
    }

    private JwtAuthenticationToken createAuthenticationToken(JwtSecurityUser user) {
        return new JwtAuthenticationToken(user, "",
                Arrays.asList(new SimpleGrantedAuthority(user.getUserRole().name())));
    }

    private String getJwtFrom(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }

        return null;
    }
}