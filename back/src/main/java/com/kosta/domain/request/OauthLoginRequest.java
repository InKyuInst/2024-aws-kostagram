package com.kosta.domain.request;

import lombok.Data;

@Data
public class OauthLoginRequest {
	private String email, name, oauthProvider, oauthProviderKey;
}
