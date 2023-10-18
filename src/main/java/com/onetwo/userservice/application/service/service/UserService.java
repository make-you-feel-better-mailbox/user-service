package com.onetwo.userservice.application.service.service;

import com.onetwo.userservice.adapter.in.web.user.response.TokenResponse;
import com.onetwo.userservice.adapter.out.persistence.entity.token.RefreshTokenEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;
import com.onetwo.userservice.application.port.in.user.command.LoginUserCommand;
import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.command.UpdateUserCommand;
import com.onetwo.userservice.application.port.in.user.command.WithdrawUserCommand;
import com.onetwo.userservice.application.port.in.user.usecase.*;
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
        UserEntity user = checkUserExistAndGetUserByUserId(userId);

        return UserConverter.of().userToUserResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto withdrawUser(WithdrawUserCommand withdrawDto) {
        UserEntity user = checkUserExistAndGetUserByUserId(withdrawDto.getUserId());

        checkUserPasswordMatched(withdrawDto.getPassword(), user);

        checkUserWithdraw(user);

        user.userWithdraw();

        return UserConverter.of().userToUserResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(String userId, UpdateUserCommand updateUserCommand) {
        UserEntity user = checkUserExistAndGetUserByUserId(userId);

        checkUserWithdraw(user);

        user.updateUserInfo(updateUserCommand);

        return UserConverter.of().userToUserResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto registerUser(RegisterUserCommand registerUserCommand) {
        if (userIdExist(registerUserCommand.getUserId()))
            throw new ResourceAlreadyExistsException("user-id already exist");

        UserEntity newUser = UserConverter.of().userRequestDtoToUser(registerUserCommand);
        newUser.setDefaultState();
        newUser.setEncodePassword(passwordEncoder.encode(registerUserCommand.getPassword()));

        UserEntity savedUser = createUserPort.createNewUser(newUser);

        // createRolePort.createNewUserRole(savedUser); Event publisher로 대체 예정

        return UserConverter.of().userToUserResponseDto(savedUser);
    }

    private boolean userIdExist(String userId) {
        return readUserPort.findByUserId(userId).isPresent();
    }

    @Override
    @Transactional
    public TokenResponse loginUser(LoginUserCommand loginUserCommand) {
        UserEntity user = checkUserExistAndGetUserByUserId(loginUserCommand.getId());

        checkUserWithdraw(user);

        checkUserPasswordMatched(loginUserCommand.getPw(), user);

        String accessToken = jwtTokenProvider.createAccessToken(user.getUserId());

        // refresh token 발급 및 저장
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUuid());
        RefreshTokenEntity token = RefreshTokenEntity.createRefreshTokenEntity(user.getUuid(), accessToken, refreshToken);

        createRefreshTokenPort.saveRefreshToken(token);

        return new TokenResponse(accessToken, refreshToken);
    }

    private void checkUserWithdraw(UserEntity user) {
        if (user.isUserWithdraw()) throw new BadRequestException("User already withdraw");
    }

    private void checkUserPasswordMatched(String requestPassword, UserEntity user) {
        if (!passwordEncoder.matches(requestPassword, user.getPassword()))
            throw new BadRequestException("Password does not match");
    }

    private UserEntity checkUserExistAndGetUserByUserId(String userId) {
        return readUserPort.findByUserId(userId).orElseThrow(() -> new NotFoundResourceException("user-id does not exist"));
    }
}
