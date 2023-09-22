package com.onetwo.userservice.controller.api;

import com.onetwo.userservice.common.GlobalUrl;
import com.onetwo.userservice.controller.UserDtoMapper;
import com.onetwo.userservice.controller.request.LoginUserRequest;
import com.onetwo.userservice.controller.request.RegisterUserRequest;
import com.onetwo.userservice.controller.response.RegisterUserResponse;
import com.onetwo.userservice.controller.response.TokenResponseDto;
import com.onetwo.userservice.service.requset.LoginDto;
import com.onetwo.userservice.service.requset.UserDto;
import com.onetwo.userservice.service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(GlobalUrl.USER_ROOT + "/{user-id}")
    private ResponseEntity<Boolean> userIdExistCheck(@PathVariable("user-id") String userId) {
        return ResponseEntity.ok().body(userService.userIdExistCheck(userId));
    }

    @PostMapping(GlobalUrl.USER_ROOT)
    private ResponseEntity<RegisterUserResponse> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        UserDto userDto = UserDtoMapper.of().registerRequestToDto(registerUserRequest);
        UserDto savedUser = userService.registerUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserDtoMapper.of().dtoToRegisterResponse(savedUser));
    }

    @PostMapping(GlobalUrl.USER_ROOT_LOGIN)
    private ResponseEntity<TokenResponseDto> loginUser(@RequestBody LoginUserRequest loginUserRequest, HttpServletRequest request) {
        LoginDto loginDto = UserDtoMapper.of().loginRequestToDto(loginUserRequest);
        TokenResponseDto loginSuccessToken = userService.loginUser(loginDto, request);
        return ResponseEntity.ok().body(loginSuccessToken);
    }
}
