package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.UpdateUserCommand;
import com.onetwo.userservice.application.port.in.user.command.UpdateUserPasswordCommand;
import com.onetwo.userservice.application.service.response.UserUpdatePasswordResponseDto;
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

    /**
     * Update user password Use case,
     * Check user exist and check current password, new password, new password check
     *
     * @param updateUserPasswordCommand request update password information
     * @return Boolean about update success
     */
    UserUpdatePasswordResponseDto updatePassword(UpdateUserPasswordCommand updateUserPasswordCommand);
}
