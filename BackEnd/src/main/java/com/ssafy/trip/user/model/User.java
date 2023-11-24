package com.ssafy.trip.user.model;

import com.ssafy.trip.jwt.security.JwtSecurityUser;
import com.ssafy.trip.user.model.typehandlers.Role;
import com.ssafy.trip.user.model.typehandlers.Sex;

import lombok.Data;

@Data
public class User extends JwtSecurityUser {
	String userId;
	String userPw;
	Sex userSex;
	int userAge;
	Role userRole;
	String userOauth;
	boolean userExit;
}
