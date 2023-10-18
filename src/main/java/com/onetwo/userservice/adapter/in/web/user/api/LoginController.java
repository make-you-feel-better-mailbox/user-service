package com.onetwo.userservice.adapter.in.web.user.api;

import com.onetwo.userservice.adapter.in.web.user.mapper.UserDtoMapper;
import com.onetwo.userservice.adapter.in.web.user.request.LoginUserRequest;
import com.onetwo.userservice.adapter.in.web.user.response.TokenResponse;
import com.onetwo.userservice.application.port.in.user.usecase.LoginUseCase;
import com.onetwo.userservice.application.port.in.user.command.LoginUserCommand;
import com.onetwo.userservice.common.GlobalUrl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginUseCase loginUseCase;

    @PostMapping(GlobalUrl.USER_LOGIN)
    public ResponseEntity<TokenResponse> loginUser(@RequestBody @Valid LoginUserRequest loginUserRequest) {
        LoginUserCommand loginUserCommand = UserDtoMapper.of().loginRequestToCommand(loginUserRequest);
        TokenResponse loginSuccessToken = loginUseCase.loginUser(loginUserCommand);
        return ResponseEntity.ok().body(loginSuccessToken);
    }
}
