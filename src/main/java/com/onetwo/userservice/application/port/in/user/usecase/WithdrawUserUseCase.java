package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.WithdrawUserCommand;
import com.onetwo.userservice.application.service.response.UserResponseDto;

public interface WithdrawUserUseCase {

    UserResponseDto withdrawUser(WithdrawUserCommand withdrawDto);
}
