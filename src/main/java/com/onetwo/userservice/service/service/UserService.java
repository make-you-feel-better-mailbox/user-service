package com.onetwo.userservice.service.service;

import com.onetwo.userservice.controller.response.TokenResponseDto;
import com.onetwo.userservice.service.requset.LoginDto;
import com.onetwo.userservice.service.requset.UserDto;

public interface UserService {
    UserDto registerUser(UserDto userDto);

    TokenResponseDto loginUser(LoginDto loginDto);
}
