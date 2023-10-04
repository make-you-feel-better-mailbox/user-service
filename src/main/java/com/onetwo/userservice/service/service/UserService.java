package com.onetwo.userservice.service.service;

import com.onetwo.userservice.controller.response.TokenResponseDto;
import com.onetwo.userservice.service.requset.LoginDto;
import com.onetwo.userservice.service.requset.UserDto;
import com.onetwo.userservice.service.response.UserIdExistCheckDto;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    UserDto registerUser(UserDto userDto);

    TokenResponseDto loginUser(LoginDto loginDto, HttpServletRequest request);

    UserIdExistCheckDto userIdExistCheck(String userId);
}
