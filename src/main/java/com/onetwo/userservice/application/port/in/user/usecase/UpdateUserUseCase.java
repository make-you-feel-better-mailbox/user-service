package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.UpdateUserCommand;
import com.onetwo.userservice.application.service.response.UserUpdateResponseDto;

public interface UpdateUserUseCase {

    /**
     * Update user use case,
     * Check user exist and update about user information
     *
     * @param updateUserCommand update user information with userId
     * @return Updated user information
     */
    UserUpdateResponseDto updateUser(UpdateUserCommand updateUserCommand);
}
