package com.onetwo.userservice.service.service;

import com.onetwo.userservice.controller.response.TokenResponseDto;
import com.onetwo.userservice.service.requset.LoginDto;
import com.onetwo.userservice.service.requset.UserRegisterDto;
import com.onetwo.userservice.service.response.UserIdExistCheckDto;
import com.onetwo.userservice.service.response.UserResponseDto;

public interface UserService {
    UserResponseDto registerUser(UserRegisterDto userRegisterDto);

    TokenResponseDto loginUser(LoginDto loginDto);

    UserIdExistCheckDto userIdExistCheck(String userId);

    UserResponseDto getUserDetailInfo(String token);
}
