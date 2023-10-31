package com.onetwo.userservice.adapter.in.web.user.mapper;

import com.onetwo.userservice.adapter.in.web.user.request.*;
import com.onetwo.userservice.adapter.in.web.user.response.*;
import com.onetwo.userservice.application.port.in.user.command.*;
import com.onetwo.userservice.application.port.in.user.response.*;

public interface UserDtoMapper {

    RegisterUserCommand registerRequestToCommand(RegisterUserRequest registerUserRequest);

    RegisterUserResponse dtoToRegisterResponse(UserRegisterResponseDto savedUser);

    LoginUserCommand loginRequestToCommand(LoginUserRequest loginUserRequest);

    UserDetailResponse dtoToUserDetailResponse(UserDetailResponseDto userDetailResponseDto);

    WithdrawUserCommand withdrawRequestToCommand(WithdrawUserRequest withdrawUserRequest, String requestUserId);

    WithdrawResponse dtoToWithdrawResponse(UserWithdrawResponseDto userWithdrawResponseDto);

    UpdateUserCommand updateRequestToCommand(String userId, UpdateUserRequest updateUserRequest);

    UpdateUserResponse dtoToUpdateResponse(UserUpdateResponseDto userUpdateResponseDto);

    LogoutUserCommand logoutRequestToCommand(String userId);

    LogoutResponse dtoToLogoutResponse(LogoutResponseDto logoutResponseDto);

    UpdateUserPasswordResponse dtoToUpdatePasswordResponse(UserUpdatePasswordResponseDto userUpdatePasswordResponseDto);

    UpdateUserPasswordCommand updatePasswordRequestToCommand(String userId, UpdateUserPasswordRequest updateUserPasswordRequest);
}
