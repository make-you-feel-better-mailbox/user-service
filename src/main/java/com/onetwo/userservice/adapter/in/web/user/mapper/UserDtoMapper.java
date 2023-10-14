package com.onetwo.userservice.adapter.in.web.user.mapper;

import com.onetwo.userservice.adapter.in.web.user.request.LoginUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.RegisterUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.UpdateUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.WithdrawUserRequest;
import com.onetwo.userservice.adapter.in.web.user.response.RegisterUserResponse;
import com.onetwo.userservice.adapter.in.web.user.response.UpdateUserResponse;
import com.onetwo.userservice.adapter.in.web.user.response.UserDetailResponse;
import com.onetwo.userservice.adapter.in.web.user.response.WithdrawResponse;
import com.onetwo.userservice.application.port.in.command.LoginUserCommand;
import com.onetwo.userservice.application.port.in.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.command.UpdateUserCommand;
import com.onetwo.userservice.application.port.in.command.WithdrawUserCommand;
import com.onetwo.userservice.application.service.response.UserResponseDto;

public class UserDtoMapper {

    private static UserDtoMapper userDtoMapper;

    public static UserDtoMapper of() {
        if (userDtoMapper == null)
            userDtoMapper = new UserDtoMapper();

        return userDtoMapper;
    }

    public RegisterUserCommand registerRequestToCommand(RegisterUserRequest registerUserRequest) {
        return new RegisterUserCommand(registerUserRequest.userId(),
                registerUserRequest.password(),
                registerUserRequest.birth(),
                registerUserRequest.nickname(),
                registerUserRequest.name(),
                registerUserRequest.email(),
                registerUserRequest.phoneNumber());
    }

    public RegisterUserResponse dtoToRegisterResponse(UserResponseDto savedUser) {
        return new RegisterUserResponse(savedUser.userId());
    }

    public LoginUserCommand loginRequestToCommand(LoginUserRequest loginUserRequest) {
        return new LoginUserCommand(loginUserRequest.userId(), loginUserRequest.password());
    }

    public UserDetailResponse dtoToUserDetailResponse(UserResponseDto userResponseDto) {
        return new UserDetailResponse(userResponseDto.userId(),
                userResponseDto.birth(),
                userResponseDto.nickname(),
                userResponseDto.name(),
                userResponseDto.email(),
                userResponseDto.phoneNumber(),
                userResponseDto.state());
    }

    public WithdrawUserCommand withdrawRequestToCommand(WithdrawUserRequest withdrawUserRequest) {
        return new WithdrawUserCommand(withdrawUserRequest.userId(), withdrawUserRequest.password());
    }

    public WithdrawResponse dtoToWithdrawResponse(UserResponseDto userResponseDto) {
        return new WithdrawResponse(userResponseDto.state());
    }

    public UpdateUserCommand updateRequestToCommand(UpdateUserRequest updateUserRequest) {
        return new UpdateUserCommand(updateUserRequest.birth(),
                updateUserRequest.nickname(),
                updateUserRequest.name(),
                updateUserRequest.email(),
                updateUserRequest.phoneNumber());
    }

    public UpdateUserResponse dtoToUpdateResponse(UserResponseDto userResponseDto) {
        return new UpdateUserResponse(userResponseDto.userId(),
                userResponseDto.birth(),
                userResponseDto.nickname(),
                userResponseDto.name(),
                userResponseDto.email(),
                userResponseDto.phoneNumber(),
                userResponseDto.state());
    }
}
