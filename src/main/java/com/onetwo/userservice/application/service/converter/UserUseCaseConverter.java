package com.onetwo.userservice.application.service.converter;

import com.onetwo.userservice.application.service.response.UserDetailResponseDto;
import com.onetwo.userservice.application.service.response.UserRegisterResponseDto;
import com.onetwo.userservice.application.service.response.UserUpdateResponseDto;
import com.onetwo.userservice.application.service.response.UserWithdrawResponseDto;
import com.onetwo.userservice.domain.user.User;

public interface UserUseCaseConverter {
    UserDetailResponseDto userToUserDetailResponseDto(User user);

    UserWithdrawResponseDto userToUserWithdrawResponseDto(User user);

    UserUpdateResponseDto userToUserUpdateResponseDto(User user);

    UserRegisterResponseDto userToUserRegisterResponseDto(User savedUser);
}
