package com.ssafy.trip.user.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.user.model.User;
import com.ssafy.trip.user.model.service.UserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	@PostMapping
	ResponseEntity<?> joinUser(@RequestBody User user) {
		try {
			userService.joinUser(user);
			User u = userService.viewUser(user.getUserId());
			if (u != null)
				return new ResponseEntity<User>(user, HttpStatus.OK);
			else return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return handleException(e);
		}
	}
	
	@GetMapping
	ResponseEntity<?> listUser() {
		try {
			List<User> users = userService.listUser();
			if (users != null)
				return new ResponseEntity<List<User>>(users, HttpStatus.OK);
			else return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			return handleException(e);
		}
	}
	
	@GetMapping("/{user-id}")
	ResponseEntity<?> viewUser(@PathVariable("user-id") String userId) {
		try {
			User user = userService.viewUser(userId);
			if (user != null)
				return new ResponseEntity<User>(user, HttpStatus.OK);
			else return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return handleException(e);
		}
	}
	
	@PutMapping("/{user-id}")
	ResponseEntity<?> modifyUser(@PathVariable("user-id") String userId, @RequestBody User user) {
		try {
			List<User> users = userService.listUser();
			int userIndex = -1;
			if (users != null) {
				for (User u : users) {
					if (u.getUserId().equals(user.getUserId())) {
						userIndex = users.indexOf(u);
						userService.modifyUser(user);
					}
				}
			}
			if (userIndex == -1) {
				userService.joinUser(user);
				return new ResponseEntity<User>(user, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<User>(user, HttpStatus.OK);
			}	
		} catch (Exception e) {
			return handleException(e);
		}
	}
	
	@DeleteMapping("/{user-id}")
	ResponseEntity<?> withdrawUser(@PathVariable("user-id") String userId) {
		try {
			userService.withdrawUser(userId);
			List<User> users = userService.listUser();
			if (users != null)
				return new ResponseEntity<List<User>>(users, HttpStatus.OK);
			else return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return handleException(e);
		}
	}
	
	@PostMapping("/login")
	ResponseEntity<?> loginUser(@RequestBody User user, HttpSession session) {
		try {
			User u = userService.loginUser(user);
			if (u != null) {
				session.setAttribute("loginInfo", u);
				return new ResponseEntity<User>(u, HttpStatus.UNAUTHORIZED);
			} else {
				return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			return handleException(e);
		}
	}
	
	@GetMapping("/logout")
	ResponseEntity<?> logoutUser(HttpSession session) {
		try {
			session.invalidate();		
			System.out.println("logout!");
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return handleException(e);
		}
	}
	
	ResponseEntity<String> handleException(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
