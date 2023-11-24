package com.ssafy.trip.user.model;

import com.ssafy.trip.jwt.security.JwtSecurityUser;
import com.ssafy.trip.user.model.typehandlers.Role;
import lombok.Data;

@Data
public class LoginUser extends JwtSecurityUser {
    private String userId;
    private Role userRole;
}
