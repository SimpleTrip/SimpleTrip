package com.ssafy.trip.email;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class EmailRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private final EmailProps props;
    private static final String KEY_PREFIX = "email::";

    public void save(String email, String authCode) {
        redisTemplate.opsForValue()
                .set(KEY_PREFIX + email, authCode, props.getAuthCodeExpirationMillis());
    }

    public Optional<String> find(String email) {
        String authCode = redisTemplate.opsForValue().get(KEY_PREFIX + email);
        return Optional.ofNullable(authCode);
    }

    public void delete(String email) {
        redisTemplate.delete(KEY_PREFIX + email);
    }
}
