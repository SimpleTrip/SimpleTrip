package com.ssafy.trip.user.model;

import com.ssafy.trip.user.model.typehandlers.Role;
import com.ssafy.trip.user.model.typehandlers.Sex;

import lombok.Data;

@Data
public class User {
	String userId;
	String userPw;
	String userName;
	Sex userSex;
	int userAge;
	Role userRole;
	String userOauth;
	boolean userExit;
}
