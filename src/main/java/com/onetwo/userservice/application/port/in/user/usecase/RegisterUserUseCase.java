package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.service.response.UserResponseDto;

public interface RegisterUserUseCase {

    UserResponseDto registerUser(RegisterUserCommand registerUserCommand);
}
