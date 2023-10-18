package com.onetwo.userservice.adapter.in.web.user.api;

import com.onetwo.userservice.adapter.in.web.user.mapper.UserDtoMapper;
import com.onetwo.userservice.adapter.in.web.user.request.RegisterUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.UpdateUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.WithdrawUserRequest;
import com.onetwo.userservice.adapter.in.web.user.response.RegisterUserResponse;
import com.onetwo.userservice.adapter.in.web.user.response.UpdateUserResponse;
import com.onetwo.userservice.adapter.in.web.user.response.UserDetailResponse;
import com.onetwo.userservice.adapter.in.web.user.response.WithdrawResponse;
import com.onetwo.userservice.application.port.in.user.usecase.ReadUserUseCase;
import com.onetwo.userservice.application.port.in.user.usecase.RegisterUserUseCase;
import com.onetwo.userservice.application.port.in.user.usecase.UpdateUserUseCase;
import com.onetwo.userservice.application.port.in.user.usecase.WithdrawUserUseCase;
import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.command.UpdateUserCommand;
import com.onetwo.userservice.application.port.in.user.command.WithdrawUserCommand;
import com.onetwo.userservice.application.service.response.UserIdExistCheckDto;
import com.onetwo.userservice.application.service.response.UserResponseDto;
import com.onetwo.userservice.common.GlobalUrl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final ReadUserUseCase readUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final WithdrawUserUseCase withdrawUserUseCase;


    @GetMapping(GlobalUrl.USER_ID + "/{user-id}")
    public ResponseEntity<UserIdExistCheckDto> userIdExistCheck(@PathVariable("user-id") String userId) {
        return ResponseEntity.ok().body(readUserUseCase.userIdExistCheck(userId));
    }

    @GetMapping(GlobalUrl.USER_ROOT)
    public ResponseEntity<UserDetailResponse> getUserDetailInfo(@AuthenticationPrincipal UserDetails userDetails) {
        String userId = userDetails.getUsername();
        UserResponseDto userResponseDto = readUserUseCase.getUserDetailInfo(userId);
        return ResponseEntity.ok().body(UserDtoMapper.of().dtoToUserDetailResponse(userResponseDto));
    }

    @PostMapping(GlobalUrl.USER_ROOT)
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        RegisterUserCommand registerUserCommand = UserDtoMapper.of().registerRequestToCommand(registerUserRequest);
        UserResponseDto savedUser = registerUserUseCase.registerUser(registerUserCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserDtoMapper.of().dtoToRegisterResponse(savedUser));
    }

    @PutMapping(GlobalUrl.USER_ROOT)
    public ResponseEntity<UpdateUserResponse> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest, @AuthenticationPrincipal UserDetails userDetails) {
        String userId = userDetails.getUsername();
        UpdateUserCommand updateUserCommand = UserDtoMapper.of().updateRequestToCommand(updateUserRequest);
        UserResponseDto userResponseDto = updateUserUseCase.updateUser(userId, updateUserCommand);
        return ResponseEntity.ok().body(UserDtoMapper.of().dtoToUpdateResponse(userResponseDto));
    }

    @DeleteMapping(GlobalUrl.USER_ROOT)
    public ResponseEntity<WithdrawResponse> withdrawUser(@RequestBody @Valid WithdrawUserRequest withdrawUserRequest) {
        WithdrawUserCommand withdrawDto = UserDtoMapper.of().withdrawRequestToCommand(withdrawUserRequest);
        UserResponseDto userResponseDto = withdrawUserUseCase.withdrawUser(withdrawDto);
        return ResponseEntity.ok().body(UserDtoMapper.of().dtoToWithdrawResponse(userResponseDto));
    }
}
