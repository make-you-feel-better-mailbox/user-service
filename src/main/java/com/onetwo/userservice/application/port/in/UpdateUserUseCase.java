package com.onetwo.userservice.application.port.in;

import com.onetwo.userservice.application.port.in.command.UpdateUserCommand;
import com.onetwo.userservice.application.service.response.UserResponseDto;

public interface UpdateUserUseCase {

    UserResponseDto updateUser(String userId, UpdateUserCommand updateUserCommand);
}
