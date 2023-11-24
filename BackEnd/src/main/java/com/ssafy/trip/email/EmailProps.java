package com.ssafy.trip.email;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Data
@Repository
public class EmailProps {
    @Value("${spring.mail.auth-code-expiration-millis}")
    private Duration authCodeExpirationMillis;
}
