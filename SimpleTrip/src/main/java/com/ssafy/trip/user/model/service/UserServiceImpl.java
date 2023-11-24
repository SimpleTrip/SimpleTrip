package com.ssafy.trip.user.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.trip.user.model.User;
import com.ssafy.trip.user.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	
	private UserMapper userMapper;

	public UserServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public void joinUser(User user) {
		userMapper.insertUser(user);
	}

	@Override
	public List<User> listUser() {
		return userMapper.listUser();
	}

	@Override
	public User viewUser(String userId) {
		return userMapper.selectUser(userId);
	}

	@Override
	public void modifyUser(User user) {
		userMapper.updateUser(user);
	}

	@Override
	public void withdrawUser(String userId) {
		userMapper.deleteUser(userId);
	}

	@Override
	public User loginUser(User user) {
		return userMapper.loginUser(user);
	}

}
