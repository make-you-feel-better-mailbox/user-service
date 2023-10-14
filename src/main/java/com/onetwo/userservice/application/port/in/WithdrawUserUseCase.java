package com.onetwo.userservice.application.port.in;

import com.onetwo.userservice.application.port.in.command.WithdrawUserCommand;
import com.onetwo.userservice.application.service.response.UserResponseDto;

public interface WithdrawUserUseCase {

    UserResponseDto withdrawUser(WithdrawUserCommand withdrawDto);
}
