package com.onetwo.userservice.application.port.out.user;

import com.onetwo.userservice.application.port.out.dto.OAuthResponseDto;
import com.onetwo.userservice.application.port.out.dto.OAuthTokenResponseDto;
import com.onetwo.userservice.common.properties.OAuthClientInfo;

public interface ReadOAuthPort {

    OAuthTokenResponseDto getToken(String authorizationCode, String registrationId);

    OAuthResponseDto getUserResource(String accessToken, String registrationId);

    OAuthClientInfo getOAuthClientInfo(String registrationId);

    String getAuthorizedURI(String registrationId);
}
