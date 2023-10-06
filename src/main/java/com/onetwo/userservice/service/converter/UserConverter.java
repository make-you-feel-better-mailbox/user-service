package com.onetwo.userservice.service.converter;

import com.onetwo.userservice.entity.user.User;
import com.onetwo.userservice.service.requset.UserRegisterDto;
import com.onetwo.userservice.service.response.UserResponseDto;

public class UserConverter {
    private static UserConverter userConverter;

    private UserConverter() {
    }

    public static UserConverter of() {
        if (userConverter == null)
            userConverter = new UserConverter();

        return userConverter;
    }

    public User userRequestDtoToUser(UserRegisterDto userRegisterDto) {
        return new User(null, userRegisterDto.userId(), null, userRegisterDto.birth(), userRegisterDto.nickname(), userRegisterDto.name(), userRegisterDto.email(),
                userRegisterDto.phoneNumber(), null);
    }

    public UserResponseDto userToUserResponseDto(User user) {
        return new UserResponseDto(user.getUserId(), user.getBirth(), user.getNickname(), user.getName(), user.getEmail(), user.getPhoneNumber(), user.getState());
    }
}
