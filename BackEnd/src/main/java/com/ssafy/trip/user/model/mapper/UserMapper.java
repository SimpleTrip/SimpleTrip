package com.ssafy.trip.user.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.user.model.User;

@Mapper
public interface UserMapper {
	
	void insertUser(User user);
	User selectUser(String userId);
	List<User> listUser();
	void updateUser(User user);
	void deleteUser(String userId);
	
	User loginUser(User user);
}
