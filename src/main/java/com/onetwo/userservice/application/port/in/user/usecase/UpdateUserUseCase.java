package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.UpdateUserCommand;
import com.onetwo.userservice.application.service.response.UserResponseDto;

public interface UpdateUserUseCase {

    UserResponseDto updateUser(String userId, UpdateUserCommand updateUserCommand);
}
