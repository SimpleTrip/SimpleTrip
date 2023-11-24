package com.ssafy.trip.jwt;

import static com.ssafy.trip.jwt.exception.JwtErrorCode.EXPIRED_TOKEN;
import static com.ssafy.trip.jwt.exception.JwtErrorCode.INVALID_TOKEN;

import com.ssafy.trip.jwt.security.JwtSecurityUser;
import com.ssafy.trip.user.model.typehandlers.Role;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import com.ssafy.trip.jwt.exception.JwtException;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    private final JwtProps jwtProps;

    @PostConstruct
    protected void encodingKey() {
        jwtProps.setSecretKey(Base64.getEncoder().encodeToString(jwtProps.getSecretKey().getBytes()));
    }

    public String issueToken(Claims Claims, Duration expiration, String secretKey){
        Date now = new Date();
        return Jwts.builder()
            .setClaims(Claims)  // 토큰에 포함될 정보
            .setIssuedAt(now)   // 발행 시간
            .setExpiration(new Date(now.getTime() + expiration.toMillis())) // 만료일
            .signWith(SignatureAlgorithm.HS256, secretKey)    // 암호화
            .compact();
    }

    private Claims createClaims(JwtSecurityUser user, String subject){
        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("userId", user.getUserId());
        claims.put("role", user.getUserRole());
        claims.put("authorities", user.getUserRole());
        return claims;
    }

    public String issueAccessToken(JwtSecurityUser user){
        return issueToken(createClaims(user, "accessToken"), jwtProps.getAccessTokenExpireTime(), jwtProps.getSecretKey());
    }

    public String issueRefreshToken(JwtSecurityUser user){
        return issueToken(createClaims(user, "refreshToken"), jwtProps.getAccessTokenExpireTime(), jwtProps.getSecretKey());
    }

    // 토큰 유효성 검사
    public boolean checkToken(String token){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtProps.getSecretKey()).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (ExpiredJwtException e){
            return false;
        }
    }

    public JwtSecurityUser parseToken(String token) {
        Jws<Claims> claims;

        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtProps.getSecretKey())
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new JwtException(EXPIRED_TOKEN);
        } catch (MalformedJwtException | SignatureException | SecurityException | IllegalArgumentException e) {
            throw new JwtException(INVALID_TOKEN);
        }

        JwtSecurityUser user = new JwtSecurityUser();
        user.setUserId((String) claims.getBody().get("userId"));
        user.setUserRole(Role.valueOf((String) claims.getBody().get("role")));
        return user;
    }

    public JwtSecurityUser parseAccessTokenByBase64(String accessToken) {
        String payload = accessToken.split("\\.")[1];
        String decodePayload = new String(Base64.getDecoder().decode(payload));
        BasicJsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> map = jsonParser.parseMap(decodePayload);
        JwtSecurityUser user = new JwtSecurityUser();
        user.setUserId((String)map.get("userId"));
        user.setUserRole(Role.valueOf((String) map.get("role")));
        return user;
    }
}