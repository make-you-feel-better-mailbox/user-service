package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.UpdateUserCommand;
import com.onetwo.userservice.application.service.response.UserUpdateResponseDto;

public interface UpdateUserUseCase {

    UserUpdateResponseDto updateUser(String userId, UpdateUserCommand updateUserCommand);
}
