package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.service.response.UserRegisterResponseDto;

public interface RegisterUserUseCase {

    UserRegisterResponseDto registerUser(RegisterUserCommand registerUserCommand);
}
