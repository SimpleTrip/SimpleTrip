package com.ssafy.trip.user.model.service;

import java.util.List;

import com.ssafy.trip.user.model.User;

public interface UserService {
	void joinUser(User user);
	List<User> listUser();
	User viewUser(String userId);
	void modifyUser(User user);
	void withdrawUser(String userId);
	
	User loginUser(User user);
}
