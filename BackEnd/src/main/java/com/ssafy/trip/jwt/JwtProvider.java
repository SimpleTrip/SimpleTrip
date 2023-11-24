package com.ssafy.trip.jwt;

import com.ssafy.trip.jwt.security.SecurityService;
import com.ssafy.trip.user.model.User;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {
    public static String headerKey = "Authorization";
    private String securityKey = "test trip key";
    private Long expiredTime = 1000 * 60L * 60L; // 유효시간 1시간

    private  final SecurityService securityService;

    public JwtProvider (SecurityService securityService){
        this.securityService = securityService;
    }

    @PostConstruct
    protected void encodingKey() {
        securityKey = Base64.getEncoder().encodeToString(securityKey.getBytes());
    }

    public String issueToken(Claims Claims, Long expiration, String secretKey){
        Date now = new Date();
        return Jwts.builder()
                .setClaims(Claims)  // 토큰에 포함될 정보
                .setIssuedAt(now)   // 발행 시간
                .setExpiration(new Date(now.getTime() + expiration)) // 만료일
                .signWith(SignatureAlgorithm.HS256, secretKey)    // 암호화
                .compact();
    }

    private Claims createClaims(User user){
        Claims claims = Jwts.claims().setSubject(user.getUserId());
        claims.put("username", user.getUserName());
        claims.put("roles", user.getUserRole());
        claims.put("sex", user.getUserSex());
        claims.put("age", user.getUserAge());
        return claims;
    }

    public String issueAccessToken(User user){
        return issueToken(createClaims(user), expiredTime, securityKey);
    }

    // 유저 인포 가져오기 = userid
    private String checkUserInfo(String token) {
        return Jwts.parser().setSigningKey(securityKey).parseClaimsJws(token).getBody().getSubject();
    }

    // 권한 조회
    public Authentication checkAuth(String token) {
        // id를 통한 유저 조회
        UserDetails userDetails = securityService.loadUserByUsername(this.checkUserInfo(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰 유효성 검사
    public boolean checkToken(String token){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(securityKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
