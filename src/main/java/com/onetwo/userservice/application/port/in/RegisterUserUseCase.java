package com.onetwo.userservice.application.port.in;

import com.onetwo.userservice.application.port.in.command.RegisterUserCommand;
import com.onetwo.userservice.application.service.response.UserResponseDto;

public interface RegisterUserUseCase {

    UserResponseDto registerUser(RegisterUserCommand registerUserCommand);
}
