package com.kosta.domain.response;

import com.kosta.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
	private Long id;
	private String email, name, oauthProvider, oauthProviderKey;
	
	public static UserResponse toDTO(User user) {
		return UserResponse.builder()
			.id(user.getId())
			.email(user.getEmail())
			.name(user.getName())
			.oauthProvider(user.getOauthProvider())
			.oauthProviderKey(user.getOauthProviderKey())
			.build();
	}
}
