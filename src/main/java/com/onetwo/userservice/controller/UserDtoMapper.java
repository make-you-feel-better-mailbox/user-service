package com.onetwo.userservice.controller;

import com.onetwo.userservice.controller.request.LoginUserRequest;
import com.onetwo.userservice.controller.request.RegisterUserRequest;
import com.onetwo.userservice.controller.response.RegisterUserResponse;
import com.onetwo.userservice.service.requset.LoginDto;
import com.onetwo.userservice.service.requset.UserDto;
import org.modelmapper.ModelMapper;

public class UserDtoMapper {

    private static UserDtoMapper userDtoMapper;

    private final ModelMapper modelMapper;

    private UserDtoMapper() {
        modelMapper = new ModelMapper();
    }

    public static UserDtoMapper of() {
        if (userDtoMapper == null)
            userDtoMapper = new UserDtoMapper();

        return userDtoMapper;
    }

    public UserDto registerRequestToDto(RegisterUserRequest registerUserRequest) {
        return new UserDto(registerUserRequest.userId(),
                registerUserRequest.password(),
                registerUserRequest.nickname(),
                registerUserRequest.name(),
                registerUserRequest.email(),
                registerUserRequest.phoneNumber());
    }

    public RegisterUserResponse dtoToRegisterResponse(UserDto savedUser) {
        return new RegisterUserResponse(savedUser.userId());
    }

    public LoginDto loginRequestToDto(LoginUserRequest loginUserRequest) {
        return new LoginDto(loginUserRequest.id(), loginUserRequest.pw());
    }
}
