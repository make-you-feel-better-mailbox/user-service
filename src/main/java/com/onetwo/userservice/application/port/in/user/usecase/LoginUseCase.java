package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.LoginUserCommand;
import com.onetwo.userservice.application.service.response.TokenResponseDto;

public interface LoginUseCase {

    /**
     * User login use case,
     * Check user exist and state
     * also check id and password matches
     * if pass all check then return Refresh token and Access token
     *
     * @param loginUserCommand userId and password
     * @return Refresh Token And Access Token
     */
    TokenResponseDto loginUser(LoginUserCommand loginUserCommand);
}
