package com.onetwo.userservice.adapter.in.web.user.mapper;

import com.onetwo.userservice.adapter.in.web.user.request.LoginUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.RegisterUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.UpdateUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.WithdrawUserRequest;
import com.onetwo.userservice.adapter.in.web.user.response.*;
import com.onetwo.userservice.application.port.in.user.command.*;
import com.onetwo.userservice.application.service.response.*;

public interface UserDtoMapper {

    RegisterUserCommand registerRequestToCommand(RegisterUserRequest registerUserRequest);

    RegisterUserResponse dtoToRegisterResponse(UserRegisterResponseDto savedUser);

    LoginUserCommand loginRequestToCommand(LoginUserRequest loginUserRequest);

    UserDetailResponse dtoToUserDetailResponse(UserDetailResponseDto userDetailResponseDto);

    WithdrawUserCommand withdrawRequestToCommand(WithdrawUserRequest withdrawUserRequest);

    WithdrawResponse dtoToWithdrawResponse(UserWithdrawResponseDto userWithdrawResponseDto);

    UpdateUserCommand updateRequestToCommand(String userId, UpdateUserRequest updateUserRequest);

    UpdateUserResponse dtoToUpdateResponse(UserUpdateResponseDto userUpdateResponseDto);

    LogoutUserCommand logoutRequestToCommand(String userId);

    LogoutResponse dtoToLogoutResponse(LogoutResponseDto logoutResponseDto);
}
