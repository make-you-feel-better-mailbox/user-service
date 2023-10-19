package com.onetwo.userservice.adapter.in.web.user.mapper;

import com.onetwo.userservice.adapter.in.web.user.request.LoginUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.RegisterUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.UpdateUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.WithdrawUserRequest;
import com.onetwo.userservice.adapter.in.web.user.response.RegisterUserResponse;
import com.onetwo.userservice.adapter.in.web.user.response.UpdateUserResponse;
import com.onetwo.userservice.adapter.in.web.user.response.UserDetailResponse;
import com.onetwo.userservice.adapter.in.web.user.response.WithdrawResponse;
import com.onetwo.userservice.application.port.in.user.command.LoginUserCommand;
import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.command.UpdateUserCommand;
import com.onetwo.userservice.application.port.in.user.command.WithdrawUserCommand;
import com.onetwo.userservice.application.service.response.UserDetailResponseDto;
import com.onetwo.userservice.application.service.response.UserRegisterResponseDto;
import com.onetwo.userservice.application.service.response.UserUpdateResponseDto;
import com.onetwo.userservice.application.service.response.UserWithdrawResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapperImpl implements UserDtoMapper {

    @Override
    public RegisterUserCommand registerRequestToCommand(RegisterUserRequest registerUserRequest) {
        return new RegisterUserCommand(registerUserRequest.userId(),
                registerUserRequest.password(),
                registerUserRequest.birth(),
                registerUserRequest.nickname(),
                registerUserRequest.name(),
                registerUserRequest.email(),
                registerUserRequest.phoneNumber());
    }

    @Override
    public RegisterUserResponse dtoToRegisterResponse(UserRegisterResponseDto savedUser) {
        return new RegisterUserResponse(savedUser.userId());
    }

    @Override
    public LoginUserCommand loginRequestToCommand(LoginUserRequest loginUserRequest) {
        return new LoginUserCommand(loginUserRequest.userId(), loginUserRequest.password());
    }

    @Override
    public UserDetailResponse dtoToUserDetailResponse(UserDetailResponseDto userDetailResponseDto) {
        return new UserDetailResponse(userDetailResponseDto.userId(),
                userDetailResponseDto.birth(),
                userDetailResponseDto.nickname(),
                userDetailResponseDto.name(),
                userDetailResponseDto.email(),
                userDetailResponseDto.phoneNumber(),
                userDetailResponseDto.state());
    }

    @Override
    public WithdrawUserCommand withdrawRequestToCommand(WithdrawUserRequest withdrawUserRequest) {
        return new WithdrawUserCommand(withdrawUserRequest.userId(), withdrawUserRequest.password());
    }

    @Override
    public WithdrawResponse dtoToWithdrawResponse(UserWithdrawResponseDto userWithdrawResponseDto) {
        return new WithdrawResponse(userWithdrawResponseDto.isWithdrawSuccess());
    }

    @Override
    public UpdateUserCommand updateRequestToCommand(UpdateUserRequest updateUserRequest) {
        return new UpdateUserCommand(updateUserRequest.birth(),
                updateUserRequest.nickname(),
                updateUserRequest.name(),
                updateUserRequest.email(),
                updateUserRequest.phoneNumber());
    }

    @Override
    public UpdateUserResponse dtoToUpdateResponse(UserUpdateResponseDto userResponseDto) {
        return new UpdateUserResponse(userResponseDto.userId(),
                userResponseDto.birth(),
                userResponseDto.nickname(),
                userResponseDto.name(),
                userResponseDto.email(),
                userResponseDto.phoneNumber(),
                userResponseDto.state());
    }

}
