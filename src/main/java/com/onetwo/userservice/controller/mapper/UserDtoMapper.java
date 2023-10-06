package com.onetwo.userservice.controller.mapper;

import com.onetwo.userservice.controller.request.LoginUserRequest;
import com.onetwo.userservice.controller.request.RegisterUserRequest;
import com.onetwo.userservice.controller.response.RegisterUserResponse;
import com.onetwo.userservice.controller.response.UserDetailResponse;
import com.onetwo.userservice.service.requset.LoginDto;
import com.onetwo.userservice.service.requset.UserRegisterDto;
import com.onetwo.userservice.service.response.UserResponseDto;

public class UserDtoMapper {

    private static UserDtoMapper userDtoMapper;

    public static UserDtoMapper of() {
        if (userDtoMapper == null)
            userDtoMapper = new UserDtoMapper();

        return userDtoMapper;
    }

    public UserRegisterDto registerRequestToDto(RegisterUserRequest registerUserRequest) {
        return new UserRegisterDto(registerUserRequest.userId(),
                registerUserRequest.password(),
                registerUserRequest.birth(),
                registerUserRequest.nickname(),
                registerUserRequest.name(),
                registerUserRequest.email(),
                registerUserRequest.phoneNumber());
    }

    public RegisterUserResponse dtoToRegisterResponse(UserResponseDto savedUser) {
        return new RegisterUserResponse(savedUser.userId());
    }

    public LoginDto loginRequestToDto(LoginUserRequest loginUserRequest) {
        return new LoginDto(loginUserRequest.userId(), loginUserRequest.password());
    }

    public UserDetailResponse dtoToUserDetailResponse(UserResponseDto userResponseDto) {
        return new UserDetailResponse(userResponseDto.userId(),
                userResponseDto.birth(),
                userResponseDto.nickname(),
                userResponseDto.name(),
                userResponseDto.email(),
                userResponseDto.phoneNumber(),
                userResponseDto.state());
    }
}
