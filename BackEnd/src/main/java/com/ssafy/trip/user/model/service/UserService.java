package com.ssafy.trip.user.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.trip.jwt.security.JwtSecurityUser;
import com.ssafy.trip.user.model.LoginUser;
import com.ssafy.trip.user.model.User;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
	void joinUser(User user);
	List<User> listUser();
	User viewUser(String userId);
	void updateUserInfo(Map<String, Object> map);
	void withdrawUser(String userId);
	void changePass(Map<String, Object> map);
	String findpass(Map<String, Object> map);

	LoginUser loginUser(User user, HttpServletResponse response);
	JwtSecurityUser refreshToken(Map<String, String> tokens, HttpServletResponse response);
}
