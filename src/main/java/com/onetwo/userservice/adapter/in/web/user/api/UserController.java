package com.onetwo.userservice.adapter.in.web.user.api;

import com.onetwo.userservice.adapter.in.web.user.mapper.UserDtoMapper;
import com.onetwo.userservice.adapter.in.web.user.request.RegisterUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.UpdateUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.WithdrawUserRequest;
import com.onetwo.userservice.adapter.in.web.user.response.RegisterUserResponse;
import com.onetwo.userservice.adapter.in.web.user.response.UpdateUserResponse;
import com.onetwo.userservice.adapter.in.web.user.response.UserDetailResponse;
import com.onetwo.userservice.adapter.in.web.user.response.WithdrawResponse;
import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.command.UpdateUserCommand;
import com.onetwo.userservice.application.port.in.user.command.WithdrawUserCommand;
import com.onetwo.userservice.application.port.in.user.usecase.ReadUserUseCase;
import com.onetwo.userservice.application.port.in.user.usecase.RegisterUserUseCase;
import com.onetwo.userservice.application.port.in.user.usecase.UpdateUserUseCase;
import com.onetwo.userservice.application.port.in.user.usecase.WithdrawUserUseCase;
import com.onetwo.userservice.application.service.response.*;
import com.onetwo.userservice.common.GlobalUrl;
import com.onetwo.userservice.domain.user.MyUserDetail;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final ReadUserUseCase readUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final WithdrawUserUseCase withdrawUserUseCase;
    private final UserDtoMapper userDtoMapper;


    @GetMapping(GlobalUrl.USER_ID + "/{user-id}")
    public ResponseEntity<UserIdExistCheckDto> userIdExistCheck(@PathVariable("user-id") String userId) {
        return ResponseEntity.ok().body(readUserUseCase.userIdExistCheck(userId));
    }

    @GetMapping(GlobalUrl.USER_ROOT)
    public ResponseEntity<UserDetailResponse> getUserDetailInfo(@AuthenticationPrincipal MyUserDetail userDetails) {
        String userId = userDetails.getUsername();
        UserDetailResponseDto userDetailResponseDto = readUserUseCase.getUserDetailInfo(userId);
        return ResponseEntity.ok().body(userDtoMapper.dtoToUserDetailResponse(userDetailResponseDto));
    }

    @PostMapping(GlobalUrl.USER_ROOT)
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        RegisterUserCommand registerUserCommand = userDtoMapper.registerRequestToCommand(registerUserRequest);
        UserRegisterResponseDto userRegisterResponseDto = registerUserUseCase.registerUser(registerUserCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDtoMapper.dtoToRegisterResponse(userRegisterResponseDto));
    }

    @PutMapping(GlobalUrl.USER_ROOT)
    public ResponseEntity<UpdateUserResponse> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest, @AuthenticationPrincipal MyUserDetail userDetails) {
        String userId = userDetails.getUsername();
        UpdateUserCommand updateUserCommand = userDtoMapper.updateRequestToCommand(updateUserRequest);
        UserUpdateResponseDto userUpdateResponseDto = updateUserUseCase.updateUser(userId, updateUserCommand);
        return ResponseEntity.ok().body(userDtoMapper.dtoToUpdateResponse(userUpdateResponseDto));
    }

    @DeleteMapping(GlobalUrl.USER_ROOT)
    public ResponseEntity<WithdrawResponse> withdrawUser(@RequestBody @Valid WithdrawUserRequest withdrawUserRequest, @AuthenticationPrincipal MyUserDetail userDetails) {
        String userId = userDetails.getUsername();
        WithdrawUserCommand withdrawDto = userDtoMapper.withdrawRequestToCommand(withdrawUserRequest);
        UserWithdrawResponseDto userWithdrawResponseDto = withdrawUserUseCase.withdrawUser(withdrawDto, userId);
        return ResponseEntity.ok().body(userDtoMapper.dtoToWithdrawResponse(userWithdrawResponseDto));
    }
}
