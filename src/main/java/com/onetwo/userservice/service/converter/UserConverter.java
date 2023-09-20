package com.onetwo.userservice.service.converter;

import com.onetwo.userservice.entity.user.User;
import com.onetwo.userservice.service.requset.UserDto;

public class UserConverter {
    private static UserConverter userConverter;

    private UserConverter() {
    }

    public static UserConverter of() {
        if (userConverter == null)
            userConverter = new UserConverter();

        return userConverter;
    }

    public User userDtoToUser(UserDto userDto) {
        return new User(null, userDto.userId(), userDto.password(), userDto.nickname(), userDto.name(), userDto.email(),
                userDto.phoneNumber(), null, null);
    }

    public UserDto userToUserDto(User userDto) {
        return null;
    }
}
