package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.OAuthLoginCommand;
import com.onetwo.userservice.application.port.in.user.response.AuthorizedURIResponseDto;
import com.onetwo.userservice.application.port.in.user.response.TokenResponseDto;

public interface LoginOAuthUseCase {

    /**
     * OAuth User login use case,
     * Check user exist and state
     * if OAuth user does not exist sign up and return Refresh token and Access token
     *
     * @param oAuthLoginCommand information about oAuth user code
     * @return Refresh Token And Access Token
     */
    TokenResponseDto loginOAuth(OAuthLoginCommand oAuthLoginCommand);

    /**
     * Get OAuth Authorized use case
     *
     * @param registrationId registration id
     * @return Authorized Request URI
     */
    AuthorizedURIResponseDto getAuthorizedURI(String registrationId);
}
