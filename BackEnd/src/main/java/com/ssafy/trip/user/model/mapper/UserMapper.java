package com.ssafy.trip.user.model.mapper;

import java.util.List;
import java.util.Map;

import com.ssafy.trip.user.model.LoginUser;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.user.model.User;

@Mapper
public interface UserMapper {
	
	void insertUser(User user);
	User selectUser(String userId);
	List<User> listUser();
	void updateUserInfo(Map<String, Object> map);
	void deleteUser(String userId);
	void changePass(Map<String, Object> map);
	LoginUser loginUser(User user);
	String findpass(String userId);
}
