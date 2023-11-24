package com.ssafy.trip.jwt.security;

import com.ssafy.trip.user.model.typehandlers.Role;
import lombok.Data;

@Data
public class JwtSecurityUser {
    private String userId;
    private Role userRole;
}
