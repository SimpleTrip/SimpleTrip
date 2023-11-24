package com.ssafy.trip.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Data
@Repository
public class JwtProps {
    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.accessToken.expire-time}")
    private Duration accessTokenExpireTime;
    @Value("${jwt.refreshToken.expire-time}")
    private Duration refreshTokenExpireTime;
}