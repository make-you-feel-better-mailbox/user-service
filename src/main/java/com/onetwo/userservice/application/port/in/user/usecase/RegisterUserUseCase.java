package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.response.UserRegisterResponseDto;

public interface RegisterUserUseCase {

    /**
     * Register user use case,
     * Check user id already exist, if exist throw exception.
     * Also encode password and register user
     *
     * @param registerUserCommand request register user information
     * @return Register Succeed User Id
     */
    UserRegisterResponseDto registerUser(RegisterUserCommand registerUserCommand);
}
