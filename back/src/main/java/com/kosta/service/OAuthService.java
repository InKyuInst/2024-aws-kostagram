package com.kosta.service;

import com.kosta.entity.User;

import jakarta.servlet.http.HttpServletResponse;

public interface OAuthService {

	User oAuthUserCheck(String code, String provider);
	String oAuthLogin(User user, HttpServletResponse res) throws Exception;
}
