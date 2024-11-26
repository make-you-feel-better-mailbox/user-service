package com.onetwo.userservice.adapter.in.web.user.api;

import com.onetwo.userservice.adapter.in.web.user.mapper.UserDtoMapper;
import com.onetwo.userservice.adapter.in.web.user.request.RegisterUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.UpdateUserPasswordRequest;
import com.onetwo.userservice.adapter.in.web.user.request.UpdateUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.WithdrawUserRequest;
import com.onetwo.userservice.adapter.in.web.user.response.*;
import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.command.UpdateUserCommand;
import com.onetwo.userservice.application.port.in.user.command.UpdateUserPasswordCommand;
import com.onetwo.userservice.application.port.in.user.command.WithdrawUserCommand;
import com.onetwo.userservice.application.port.in.user.response.*;
import com.onetwo.userservice.application.port.in.user.usecase.ReadUserUseCase;
import com.onetwo.userservice.application.port.in.user.usecase.RegisterUserUseCase;
import com.onetwo.userservice.application.port.in.user.usecase.UpdateUserUseCase;
import com.onetwo.userservice.application.port.in.user.usecase.WithdrawUserUseCase;
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

    /**
     * Check user id exist inbound adapter
     *
     * @param userId userId
     * @return Boolean about user id exist
     */
    @GetMapping(GlobalUrl.USER_ID + GlobalUrl.PATH_VARIABLE_WITH_USER_ID)
    public ResponseEntity<UserIdExistCheckDto> userIdExistCheck(@PathVariable(GlobalUrl.PATH_VARIABLE_USER_ID) String userId) {
        return ResponseEntity.ok().body(readUserUseCase.userIdExistCheck(userId));
    }

    /**
     * Get users detail information inbound adapter
     *
     * @param userDetails user authentication information
     * @return detail information about user
     */
    @GetMapping(GlobalUrl.USER_ROOT)
    public ResponseEntity<UserDetailResponse> getUserDetailInfo(@AuthenticationPrincipal MyUserDetail userDetails) {
        String userId = userDetails.getUsername();
        UserDetailResponseDto userDetailResponseDto = readUserUseCase.getUserDetailInfo(userId);
        return ResponseEntity.ok().body(userDtoMapper.dtoToUserDetailResponse(userDetailResponseDto));
    }

    /**
     * Get users information inbound adapter
     *
     * @param userId user id
     * @return detail information about user
     */
    @GetMapping(GlobalUrl.USER_ROOT + GlobalUrl.PATH_VARIABLE_WITH_USER_ID)
    public ResponseEntity<UserInfoResponse> getUserDetailInfo(@PathVariable(GlobalUrl.PATH_VARIABLE_USER_ID) String userId) {
        UserInfoResponseDto userInfoResponseDto = readUserUseCase.getUserInfo(userId);
        return ResponseEntity.ok().body(userDtoMapper.dtoToUserInfoResponse(userInfoResponseDto));
    }

    /**
     * Register user inbound adapter
     *
     * @param registerUserRequest Register request user information
     * @return Registered user id
     */
    @PostMapping(GlobalUrl.USER_ROOT)
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        RegisterUserCommand registerUserCommand = userDtoMapper.registerRequestToCommand(registerUserRequest);
        UserRegisterResponseDto userRegisterResponseDto = registerUserUseCase.registerUser(registerUserCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDtoMapper.dtoToRegisterResponse(userRegisterResponseDto));
    }

    /**
     * Update users information inbound adapter
     *
     * @param updateUserRequest request update user information
     * @param userDetails       user authentication information
     * @return updated detail information about user
     */
    @PutMapping(GlobalUrl.USER_ROOT)
    public ResponseEntity<UpdateUserResponse> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest, @AuthenticationPrincipal MyUserDetail userDetails) {
        String userId = userDetails.getUsername();
        UpdateUserCommand updateUserCommand = userDtoMapper.updateRequestToCommand(userId, updateUserRequest);
        UserUpdateResponseDto userUpdateResponseDto = updateUserUseCase.updateUser(updateUserCommand);
        return ResponseEntity.ok().body(userDtoMapper.dtoToUpdateResponse(userUpdateResponseDto));
    }

    /**
     * Withdraw users information inbound adapter
     *
     * @param withdrawUserRequest request withdraw user information
     * @param userDetails         user authentication information
     * @return Boolean about withdraw success
     */
    @DeleteMapping(GlobalUrl.USER_ROOT)
    public ResponseEntity<WithdrawResponse> withdrawUser(@RequestBody @Valid WithdrawUserRequest withdrawUserRequest, @AuthenticationPrincipal MyUserDetail userDetails) {
        String requestUserId = userDetails.getUsername();
        WithdrawUserCommand withdrawDto = userDtoMapper.withdrawRequestToCommand(withdrawUserRequest, requestUserId);
        UserWithdrawResponseDto userWithdrawResponseDto = withdrawUserUseCase.withdrawUser(withdrawDto);
        return ResponseEntity.ok().body(userDtoMapper.dtoToWithdrawResponse(userWithdrawResponseDto));
    }

    /**
     * Update user password inbound adapter
     *
     * @param updateUserPasswordRequest request update password information
     * @param userDetails               user authentication information
     * @return Boolean about update success
     */
    @PutMapping(GlobalUrl.USER_PW)
    public ResponseEntity<UpdateUserPasswordResponse> updatePassword(@RequestBody @Valid UpdateUserPasswordRequest updateUserPasswordRequest, @AuthenticationPrincipal MyUserDetail userDetails) {
        String userId = userDetails.getUsername();
        UpdateUserPasswordCommand updateUserPasswordCommand = userDtoMapper.updatePasswordRequestToCommand(userId, updateUserPasswordRequest);
        UserUpdatePasswordResponseDto userUpdatePasswordResponseDto = updateUserUseCase.updatePassword(updateUserPasswordCommand);
        return ResponseEntity.ok().body(userDtoMapper.dtoToUpdatePasswordResponse(userUpdatePasswordResponseDto));
    }
}
