package com.onetwo.userservice.application.service.converter;

import com.onetwo.userservice.application.service.response.UserDetailResponseDto;
import com.onetwo.userservice.application.service.response.UserRegisterResponseDto;
import com.onetwo.userservice.application.service.response.UserUpdateResponseDto;
import com.onetwo.userservice.application.service.response.UserWithdrawResponseDto;
import com.onetwo.userservice.domain.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserUseCaseConverterImpl implements UserUseCaseConverter {
    @Override
    public UserDetailResponseDto userToUserDetailResponseDto(User user) {
        return new UserDetailResponseDto(
                user.getUserId(),
                user.getBirth(),
                user.getNickname(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getState()
        );
    }

    @Override
    public UserWithdrawResponseDto userToUserWithdrawResponseDto(User user) {
        return new UserWithdrawResponseDto(user.getState());
    }

    @Override
    public UserUpdateResponseDto userToUserUpdateResponseDto(User user) {
        return new UserUpdateResponseDto(
                user.getUserId(),
                user.getBirth(),
                user.getNickname(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getState()
        );
    }

    @Override
    public UserRegisterResponseDto userToUserRegisterResponseDto(User savedUser) {
        return new UserRegisterResponseDto(savedUser.getUserId());
    }
}
