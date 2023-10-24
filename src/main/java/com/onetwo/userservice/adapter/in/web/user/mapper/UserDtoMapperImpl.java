package com.onetwo.userservice.adapter.in.web.user.mapper;

import com.onetwo.userservice.adapter.in.web.user.request.LoginUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.RegisterUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.UpdateUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.WithdrawUserRequest;
import com.onetwo.userservice.adapter.in.web.user.response.*;
import com.onetwo.userservice.application.port.in.user.command.*;
import com.onetwo.userservice.application.service.response.*;
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
    public WithdrawUserCommand withdrawRequestToCommand(WithdrawUserRequest withdrawUserRequest, String requestUserId) {
        return new WithdrawUserCommand(withdrawUserRequest.userId(), withdrawUserRequest.password(), requestUserId);
    }

    @Override
    public WithdrawResponse dtoToWithdrawResponse(UserWithdrawResponseDto userWithdrawResponseDto) {
        return new WithdrawResponse(userWithdrawResponseDto.isWithdrawSuccess());
    }

    @Override
    public UpdateUserCommand updateRequestToCommand(String userId, UpdateUserRequest updateUserRequest) {
        return new UpdateUserCommand(userId,
                updateUserRequest.birth(),
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

    @Override
    public LogoutUserCommand logoutRequestToCommand(String userId) {
        return new LogoutUserCommand(userId);
    }

    @Override
    public LogoutResponse dtoToLogoutResponse(LogoutResponseDto logoutResponseDto) {
        return new LogoutResponse(logoutResponseDto.isLogoutSuccess());
    }

}
