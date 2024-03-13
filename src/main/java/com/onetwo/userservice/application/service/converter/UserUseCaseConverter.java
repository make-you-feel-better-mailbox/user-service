package com.onetwo.userservice.application.service.converter;

import com.onetwo.userservice.application.port.in.user.response.*;
import com.onetwo.userservice.domain.user.User;

public interface UserUseCaseConverter {
    UserDetailResponseDto userToUserDetailResponseDto(User user);

    UserWithdrawResponseDto userToUserWithdrawResponseDto(User user);

    UserUpdateResponseDto userToUserUpdateResponseDto(User user);

    UserRegisterResponseDto userToUserRegisterResponseDto(User savedUser);

    UserIdExistCheckDto toUserIdExistCheckDto(Boolean isUserIdExist);

    UserUpdatePasswordResponseDto toUserUpdatePasswordResponseDto(boolean userPasswordUpdated);

    UserInfoResponseDto userToUserInfoResponseDto(User user);
}
