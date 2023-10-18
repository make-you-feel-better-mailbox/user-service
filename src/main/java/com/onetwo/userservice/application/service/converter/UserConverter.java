package com.onetwo.userservice.application.service.converter;

import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;
import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.service.response.UserResponseDto;

public class UserConverter {
    private static UserConverter userConverter;

    private UserConverter() {
    }

    public static UserConverter of() {
        if (userConverter == null)
            userConverter = new UserConverter();

        return userConverter;
    }

    public UserEntity userRequestDtoToUser(RegisterUserCommand registerUserCommand) {
        return new UserEntity(null, registerUserCommand.getUserId(),
                null,
                registerUserCommand.getBirth(),
                registerUserCommand.getNickname(),
                registerUserCommand.getName(),
                registerUserCommand.getEmail(),
                registerUserCommand.getPhoneNumber(), null);
    }

    public UserResponseDto userToUserResponseDto(UserEntity user) {
        return new UserResponseDto(user.getUserId(),
                user.getBirth(),
                user.getNickname(),
                user.getName(),
                user.getEmail(), user.getPhoneNumber(), user.getState());
    }
}
