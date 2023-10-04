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
        return new User(null, userDto.userId(), null, userDto.birth(), userDto.nickname(), userDto.name(), userDto.email(),
                userDto.phoneNumber(), null);
    }

    public UserDto userToUserDto(User user) {
        return new UserDto(user.getUserId(), user.getPassword(), user.getBirth(), user.getNickname(), user.getName(), user.getEmail(), user.getPhoneNumber());
    }
}
