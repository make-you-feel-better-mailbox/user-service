package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.LogoutUserCommand;
import com.onetwo.userservice.application.service.response.LogoutResponseDto;

public interface LogoutUseCase {
    LogoutResponseDto logoutUser(LogoutUserCommand logoutUserCommand);
}
