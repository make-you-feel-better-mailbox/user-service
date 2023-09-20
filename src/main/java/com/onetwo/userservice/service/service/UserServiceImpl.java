package com.onetwo.userservice.service.service;

import com.onetwo.userservice.controller.response.TokenResponseDto;
import com.onetwo.userservice.entity.user.User;
import com.onetwo.userservice.repository.UserRepository;
import com.onetwo.userservice.service.converter.UserConverter;
import com.onetwo.userservice.service.requset.LoginDto;
import com.onetwo.userservice.service.requset.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDto registerUser(UserDto userDto) {
        User newUser = UserConverter.of().userDtoToUser(userDto);
        newUser.setDefaultRoleAndState();

        User savedUser = userRepository.save(newUser);

        return UserConverter.of().userToUserDto(savedUser);
    }

    @Override
    public TokenResponseDto loginUser(LoginDto loginDto) {
        return null;
    }
}
