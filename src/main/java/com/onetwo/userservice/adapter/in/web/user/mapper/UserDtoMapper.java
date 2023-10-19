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

public interface UserDtoMapper {

    RegisterUserCommand registerRequestToCommand(RegisterUserRequest registerUserRequest);

    RegisterUserResponse dtoToRegisterResponse(UserRegisterResponseDto savedUser);

    LoginUserCommand loginRequestToCommand(LoginUserRequest loginUserRequest);

    UserDetailResponse dtoToUserDetailResponse(UserDetailResponseDto userDetailResponseDto);

    WithdrawUserCommand withdrawRequestToCommand(WithdrawUserRequest withdrawUserRequest);

    WithdrawResponse dtoToWithdrawResponse(UserWithdrawResponseDto userWithdrawResponseDto);

    UpdateUserCommand updateRequestToCommand(UpdateUserRequest updateUserRequest);

    UpdateUserResponse dtoToUpdateResponse(UserUpdateResponseDto userUpdateResponseDto);
}
