package com.onetwo.userservice.application.service.converter;

import com.onetwo.userservice.application.port.in.user.response.*;
import com.onetwo.userservice.domain.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserUseCaseConverterImpl implements UserUseCaseConverter {
    @Override
    public UserDetailResponseDto userToUserDetailResponseDto(User user) {
        return new UserDetailResponseDto(
                user.getUserId(),
                user.getNickname(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.isOauth(),
                user.getRegistrationId(),
                user.isState()
        );
    }

    @Override
    public UserWithdrawResponseDto userToUserWithdrawResponseDto(User user) {
        return new UserWithdrawResponseDto(user.isState());
    }

    @Override
    public UserUpdateResponseDto userToUserUpdateResponseDto(User user) {
        return new UserUpdateResponseDto(
                user.getUserId(),
                user.getNickname(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.isState()
        );
    }

    @Override
    public UserRegisterResponseDto userToUserRegisterResponseDto(User savedUser) {
        return new UserRegisterResponseDto(savedUser.getUserId());
    }

    @Override
    public UserIdExistCheckDto toUserIdExistCheckDto(Boolean isUserIdExist) {
        return new UserIdExistCheckDto(isUserIdExist);
    }

    @Override
    public UserUpdatePasswordResponseDto toUserUpdatePasswordResponseDto(boolean userPasswordUpdated) {
        return new UserUpdatePasswordResponseDto(userPasswordUpdated);
    }
}
