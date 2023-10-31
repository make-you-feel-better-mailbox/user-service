package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.LogoutUserCommand;
import com.onetwo.userservice.application.port.in.user.response.LogoutResponseDto;

public interface LogoutUseCase {

    /**
     * User logout use case,
     * Delete if Refresh Token exist
     *
     * @param logoutUserCommand userId
     * @return Boolean about logout success
     */
    LogoutResponseDto logoutUser(LogoutUserCommand logoutUserCommand);
}
