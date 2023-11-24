package com.ssafy.trip.user.model.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.ssafy.trip.email.EmailRepository;
import com.ssafy.trip.email.exception.EmailErrorCode;
import com.ssafy.trip.email.exception.EmailException;
import com.ssafy.trip.email.model.EmailService;
import com.ssafy.trip.jwt.JwtProps;
import com.ssafy.trip.jwt.JwtProvider;
import com.ssafy.trip.jwt.RefreshTokenRepository;
import com.ssafy.trip.jwt.exception.JwtErrorCode;
import com.ssafy.trip.jwt.exception.JwtException;
import com.ssafy.trip.jwt.security.JwtSecurityUser;
import com.ssafy.trip.user.exception.UserErrorCode;
import com.ssafy.trip.user.exception.UserException;
import com.ssafy.trip.user.model.LoginUser;
import com.ssafy.trip.user.model.typehandlers.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ssafy.trip.user.model.User;
import com.ssafy.trip.user.model.mapper.UserMapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserMapper userMapper;
	private final JwtProvider jwtProvider;
	private final JwtProps jwtProps;
	private final RefreshTokenRepository refreshTokenRepository;
	private final EmailRepository emailRepository;
	private final EmailService emailService;

	@Override
	public void joinUser(User user) {
		user.setUserRole(Role.valueOf("ROLE_USER"));
		User u = userMapper.selectUser(user.getUserId());
		if(u != null) throw new UserException(UserErrorCode.EXIST_USER);
		userMapper.insertUser(user);
	}

	@Override
	public List<User> listUser() {
		return userMapper.listUser();
	}

	@Override
	public String findpass(String userId) {
		String pass = userMapper.findpass(userId);
		if(pass == null || pass.equals("")) throw new UserException(UserErrorCode.UNMATCHED_INFO);
		return pass;
	}

	@Override
	public User viewUser(String userId) {
		User user = userMapper.selectUser(userId);
		if(user == null) throw new UserException((UserErrorCode.UNMATCHED_ID));
		return user;
	}

	@Override
	public void updateUserInfo(Map<String, Object> map) {
		User tmpUser = new User();
		tmpUser.setUserId((String) map.get("userId"));
		tmpUser.setUserPw((String) map.get("userPw"));
		LoginUser u = userMapper.loginUser(tmpUser);
		if(u == null) throw new UserException(UserErrorCode.UNMATCHED_PASS);
		userMapper.updateUserInfo(map);
	}

	@Override
	public void withdrawUser(String userId) {
		userMapper.deleteUser(userId);
	}

	@Override
	public void changePass(Map<String, Object> map) {
		User tmpUser = new User();
		tmpUser.setUserId((String) map.get("userId"));
		tmpUser.setUserPw((String) map.get("beforePass"));
		LoginUser u = userMapper.loginUser(tmpUser);
		if(u == null) throw new UserException(UserErrorCode.UNMATCHED_PASS);
		userMapper.changePass(map);
	}

	@Override
	public LoginUser loginUser(User user, HttpServletResponse response) {
		User checkExitUser = userMapper.selectUser(user.getUserId());
		if(checkExitUser != null && checkExitUser.isUserExit()) throw new UserException(UserErrorCode.EXIT_USER);
		LoginUser u = userMapper.loginUser(user);
		if(u == null) throw new UserException(UserErrorCode.NO_USER);

		String accessToken = jwtProvider.issueAccessToken(u);
		String refreshToken = jwtProvider.issueRefreshToken(u);
		refreshTokenRepository.save(refreshToken, accessToken);

		Cookie accessCookie = new Cookie("accessToken", accessToken);
		accessCookie.setMaxAge((int) jwtProps.getRefreshTokenExpireTime().getSeconds());
		accessCookie.setPath("/");
		accessCookie.setSecure(false);
		Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
		refreshCookie.setMaxAge((int) jwtProps.getRefreshTokenExpireTime().getSeconds());
		refreshCookie.setPath("/");
		accessCookie.setSecure(false);

		response.addCookie(accessCookie);
		response.addCookie(refreshCookie);

		return u;
	}

	@Override
	public JwtSecurityUser refreshToken(Map<String, String> tokens, HttpServletResponse response) {
		String paramRefreshToken = tokens.get("refreshToken");
		String paramAccessToken = tokens.get("accessToken");

		String accessToken = refreshTokenRepository.find(paramRefreshToken)
				.orElseThrow(() -> new JwtException(JwtErrorCode.INVALID_TOKEN));

		if (!accessToken.equals(paramAccessToken)) throw new JwtException(JwtErrorCode.INVALID_TOKEN);

		JwtSecurityUser user = jwtProvider.parseAccessTokenByBase64(accessToken);
		String newAccessToken = jwtProvider.issueAccessToken(user);
		String newRefreshToken = jwtProvider.issueRefreshToken(user);

		refreshTokenRepository.delete(paramRefreshToken);
		refreshTokenRepository.save(newRefreshToken, newAccessToken);

		Cookie accessCookie = new Cookie("accessToken", newAccessToken);
		accessCookie.setMaxAge((int) jwtProps.getRefreshTokenExpireTime().getSeconds());
		accessCookie.setPath("/");
		accessCookie.setSecure(false);
		Cookie refreshCookie = new Cookie("refreshToken", newRefreshToken);
		refreshCookie.setMaxAge((int) jwtProps.getRefreshTokenExpireTime().getSeconds());
		refreshCookie.setPath("/");
		accessCookie.setSecure(false);

		response.addCookie(accessCookie);
		response.addCookie(refreshCookie);

		return user;
	}

	@Override
	public void sendCodeToEmail(String toEmail) {
		String title = "SimpleTrip 이메일 인증 번호";
		String authCode = this.createCode();
		emailService.sendEmail(toEmail, title, authCode);
		emailRepository.save(toEmail, authCode);
	}

	private String createCode() {
		int lenth = 6;
		try {
			Random random = SecureRandom.getInstanceStrong();
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < lenth; i++) {
				builder.append(random.nextInt(10));
			}
			return builder.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new EmailException(EmailErrorCode.CAN_NOT_MAKE_CODE);
		}
	}

	@Override
	public void verifiedCode(String email, String authCode) {
		String getAuthCode = emailRepository.find(email)
				.orElseThrow(() -> new UserException(UserErrorCode.UNMATCHED_CODE));
		if(!authCode.equals(getAuthCode)){
			throw new UserException(UserErrorCode.UNMATCHED_CODE);
		}
	}
}
