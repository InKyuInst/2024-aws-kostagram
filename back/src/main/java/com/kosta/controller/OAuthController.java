package com.kosta.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.domain.request.OauthLoginRequest;
import com.kosta.domain.response.LoginResponse;
import com.kosta.domain.response.UserResponse;
import com.kosta.entity.User;
import com.kosta.service.OAuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class OAuthController {
	private final OAuthService oAuthService;
	
	@GetMapping("/{provider}")
	public ResponseEntity<?> oAuthUserCheck(@RequestParam("code") final String code, @PathVariable("provider") final String provider, HttpServletResponse res) {
		log.info("들어온 코드 값 > {}", code);
		User user = oAuthService.oAuthUserCheck(code, provider);
		if (user.getEmail() == null) {
			return ResponseEntity.ok(UserResponse.toDTO(user));
		} else {
			try {
				String accessToken = oAuthService.oAuthLogin(user, res);
				LoginResponse loginResponse = LoginResponse
					.builder()
					.accessToken(accessToken)
					.build();
				return ResponseEntity.ok(loginResponse);
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			}
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> oAuthLogin(@RequestBody OauthLoginRequest oauthLoginRequest, HttpServletResponse res) {
		log.info(oauthLoginRequest.toString());
		User user = User.builder()
			.name(oauthLoginRequest.getName())
			.email(oauthLoginRequest.getEmail())
			.oauthProvider(oauthLoginRequest.getOauthProvider())
			.oauthProviderKey(oauthLoginRequest.getOauthProviderKey())
			.build();
		log.info(user.toString());
		try {
			String accessToken = oAuthService.oAuthLogin(user, res);
			LoginResponse loginResponse = LoginResponse
				.builder()
				.accessToken(accessToken)
				.build();
			return ResponseEntity.ok(loginResponse);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}













