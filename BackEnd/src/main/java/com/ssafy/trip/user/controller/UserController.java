package com.ssafy.trip.user.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssafy.trip.common.Message;
import com.ssafy.trip.email.EmailRepository;
import com.ssafy.trip.jwt.RefreshTokenRepository;
import com.ssafy.trip.jwt.security.JwtSecurityUser;
import com.ssafy.trip.user.exception.UserErrorCode;
import com.ssafy.trip.user.exception.UserException;
import com.ssafy.trip.user.model.LoginUser;
import com.ssafy.trip.user.model.typehandlers.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ssafy.trip.user.model.User;
import com.ssafy.trip.user.model.service.UserService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final UserService userService;
	private final RefreshTokenRepository refreshTokenRepository;

	@PostMapping
	ResponseEntity<?> joinUser(@RequestBody User user) {
		userService.joinUser(user);
		return ResponseEntity.ok().body(Message.success());
	}

	@GetMapping("/{userId}")
	@PreAuthorize("isAuthenticated() and #userId == authentication.principal.userId")
	ResponseEntity<?> viewUser(@PathVariable("userId") String userId) {
		return ResponseEntity.ok().body(Message.success(userService.viewUser(userId)));
	}

	@PutMapping("/changePass")
	@PreAuthorize("isAuthenticated() and #map['userId'] == authentication.principal.userId")
	ResponseEntity<?> changePass(@RequestBody Map<String, Object> map) {
		userService.changePass(map);
		return ResponseEntity.ok().body(Message.success());
	}

	@PutMapping("/modifyInfo")
	@PreAuthorize("isAuthenticated() and #map['userId'] == authentication.principal.userId")
	ResponseEntity<?> updateUserInfo(@RequestBody Map<String, Object> map) {
		userService.updateUserInfo(map);
		return ResponseEntity.ok().body(Message.success(userService.viewUser((String) map.get("userId"))));
	}

	@PutMapping("/delete")
	@PreAuthorize("isAuthenticated() and #map['userId'] == authentication.principal.userId")
	ResponseEntity<?> withdrawUser(@RequestBody Map<String, String> map, @CookieValue(name = "refreshToken", required = false) Cookie refreshToken) {
		userService.withdrawUser(map.get("userId"));
		if(refreshToken != null) refreshTokenRepository.delete(refreshToken.getValue());
		return ResponseEntity.ok().body(Message.success());
	}


	@PostMapping("/login")
	ResponseEntity<?> loginUser(@RequestBody User user, HttpServletResponse response) {
		LoginUser u = userService.loginUser(user, response);
		return ResponseEntity.ok().body(Message.success(u));
	}

	@GetMapping("/logout")
	ResponseEntity<?> logoutUser(@CookieValue(name = "refreshToken", required = false) Cookie refreshToken) {
		if(refreshToken != null) refreshTokenRepository.delete(refreshToken.getValue());
		return ResponseEntity.ok().body(Message.success());
	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> tokens, HttpServletResponse response) {
		return ResponseEntity.ok().body(Message.success(userService.refreshToken(tokens, response)));
	}

	@GetMapping("/sendEmail")
	ResponseEntity<?> sendEmail(@RequestParam Map<String, Object> params) {
		userService.sendCodeToEmail((String) params.get("email"));
		return ResponseEntity.ok().body(Message.success());
	}

	@GetMapping("/checkEmail")
	ResponseEntity<?> checkEmail(@RequestParam Map<String, Object> params) {
		userService.verifiedCode((String) params.get("email"), (String) params.get("authCode"));
		return ResponseEntity.ok().body(Message.success(userService.findpass((String) params.get("email"))));
	}
}
