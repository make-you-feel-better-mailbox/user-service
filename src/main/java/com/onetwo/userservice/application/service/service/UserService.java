package com.onetwo.userservice.application.service.service;

import com.onetwo.userservice.adapter.in.web.user.response.TokenResponse;
import com.onetwo.userservice.adapter.out.persistence.entity.redis.RefreshToken;
import com.onetwo.userservice.adapter.out.persistence.entity.user.User;
import com.onetwo.userservice.application.port.in.*;
import com.onetwo.userservice.application.port.in.command.LoginUserCommand;
import com.onetwo.userservice.application.port.in.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.command.UpdateUserCommand;
import com.onetwo.userservice.application.port.in.command.WithdrawUserCommand;
import com.onetwo.userservice.application.port.out.role.CreateRolePort;
import com.onetwo.userservice.application.port.out.token.CreateRefreshTokenPort;
import com.onetwo.userservice.application.port.out.user.CreateUserPort;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.application.service.converter.UserConverter;
import com.onetwo.userservice.application.service.response.UserIdExistCheckDto;
import com.onetwo.userservice.application.service.response.UserResponseDto;
import com.onetwo.userservice.common.exceptions.BadRequestException;
import com.onetwo.userservice.common.exceptions.NotFoundResourceException;
import com.onetwo.userservice.common.exceptions.ResourceAlreadyExistsException;
import com.onetwo.userservice.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements RegisterUserUseCase, LoginUseCase, ReadUserUseCase, UpdateUserUseCase, WithdrawUserUseCase {

    private final CreateRolePort createRolePort;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CreateRefreshTokenPort createRefreshTokenPort;
    private final ReadUserPort readUserPort;
    private final CreateUserPort createUserPort;

    @Override
    @Transactional(readOnly = true)
    public UserIdExistCheckDto userIdExistCheck(String userId) {
        return new UserIdExistCheckDto(userIdExist(userId));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserDetailInfo(String userId) {
        User user = checkUserExistAndGetUserByUserId(userId);

        return UserConverter.of().userToUserResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto withdrawUser(WithdrawUserCommand withdrawDto) {
        User user = checkUserExistAndGetUserByUserId(withdrawDto.getUserId());

        checkUserPasswordMatched(withdrawDto.getPassword(), user);

        checkUserWithdraw(user);

        user.userWithdraw();

        return UserConverter.of().userToUserResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(String userId, UpdateUserCommand updateUserCommand) {
        User user = checkUserExistAndGetUserByUserId(userId);

        checkUserWithdraw(user);

        user.updateUserInfo(updateUserCommand);

        return UserConverter.of().userToUserResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto registerUser(RegisterUserCommand registerUserCommand) {
        if (userIdExist(registerUserCommand.getUserId()))
            throw new ResourceAlreadyExistsException("user-id already exist");

        User newUser = UserConverter.of().userRequestDtoToUser(registerUserCommand);
        newUser.setDefaultState();
        newUser.setEncodePassword(passwordEncoder.encode(registerUserCommand.getPassword()));

        User savedUser = createUserPort.createNewUser(newUser);

        createRolePort.createNewUserRole(savedUser);

        return UserConverter.of().userToUserResponseDto(savedUser);
    }

    private boolean userIdExist(String userId) {
        return readUserPort.findByUserId(userId).isPresent();
    }

    @Override
    @Transactional
    public TokenResponse loginUser(LoginUserCommand loginUserCommand) {
        User user = checkUserExistAndGetUserByUserId(loginUserCommand.getId());

        checkUserWithdraw(user);

        checkUserPasswordMatched(loginUserCommand.getPw(), user);

        String accessToken = jwtTokenProvider.createAccessToken(user.getUserId());

        // refresh token 발급 및 저장
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUuid());
        RefreshToken token = RefreshToken.createRefreshTokenEntity(user.getUuid(), accessToken, refreshToken);

        createRefreshTokenPort.saveRefreshToken(token);

        return new TokenResponse(accessToken, refreshToken);
    }

    private void checkUserWithdraw(User user) {
        if (user.isUserWithdraw()) throw new BadRequestException("User already withdraw");
    }

    private void checkUserPasswordMatched(String requestPassword, User user) {
        if (!passwordEncoder.matches(requestPassword, user.getPassword()))
            throw new BadRequestException("Password does not match");
    }

    private User checkUserExistAndGetUserByUserId(String userId) {
        return readUserPort.findByUserId(userId).orElseThrow(() -> new NotFoundResourceException("user-id does not exist"));
    }
}
