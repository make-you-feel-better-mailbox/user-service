package com.onetwo.userservice.controller.api;

import com.onetwo.userservice.common.GlobalUrl;
import com.onetwo.userservice.controller.mapper.UserDtoMapper;
import com.onetwo.userservice.controller.request.LoginUserRequest;
import com.onetwo.userservice.controller.request.RegisterUserRequest;
import com.onetwo.userservice.controller.response.RegisterUserResponse;
import com.onetwo.userservice.controller.response.TokenResponseDto;
import com.onetwo.userservice.controller.response.UserDetailResponse;
import com.onetwo.userservice.service.requset.LoginDto;
import com.onetwo.userservice.service.requset.UserRegisterDto;
import com.onetwo.userservice.service.response.UserIdExistCheckDto;
import com.onetwo.userservice.service.response.UserResponseDto;
import com.onetwo.userservice.service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(GlobalUrl.USER_ID + "/{user-id}")
    public ResponseEntity<UserIdExistCheckDto> userIdExistCheck(@PathVariable("user-id") String userId) {
        return ResponseEntity.ok().body(userService.userIdExistCheck(userId));
    }

    @GetMapping(GlobalUrl.USER_ROOT)
    public ResponseEntity<UserDetailResponse> getUserDetailInfo(@RequestHeader String token) {
        UserResponseDto userDetailInfo = userService.getUserDetailInfo(token);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserDtoMapper.of().dtoToUserDetailResponse(userDetailInfo));
    }

    @PostMapping(GlobalUrl.USER_ROOT)
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        UserRegisterDto userRegisterDto = UserDtoMapper.of().registerRequestToDto(registerUserRequest);
        UserResponseDto savedUser = userService.registerUser(userRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserDtoMapper.of().dtoToRegisterResponse(savedUser));
    }

    @PostMapping(GlobalUrl.USER_LOGIN)
    public ResponseEntity<TokenResponseDto> loginUser(@RequestBody LoginUserRequest loginUserRequest) {
        LoginDto loginDto = UserDtoMapper.of().loginRequestToDto(loginUserRequest);
        TokenResponseDto loginSuccessToken = userService.loginUser(loginDto);
        return ResponseEntity.ok().body(loginSuccessToken);
    }
}
