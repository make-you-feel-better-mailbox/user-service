package com.onetwo.userservice.application.service.service;

import com.onetwo.userservice.application.port.in.user.command.LoginUserCommand;
import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.command.UpdateUserCommand;
import com.onetwo.userservice.application.port.in.user.command.WithdrawUserCommand;
import com.onetwo.userservice.application.port.in.user.usecase.*;
import com.onetwo.userservice.application.port.out.token.CreateRefreshTokenPort;
import com.onetwo.userservice.application.port.out.user.CreateUserPort;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.application.port.out.user.UpdateUserPort;
import com.onetwo.userservice.application.service.converter.UserUseCaseConverter;
import com.onetwo.userservice.application.service.response.*;
import com.onetwo.userservice.common.exceptions.BadRequestException;
import com.onetwo.userservice.common.exceptions.NotFoundResourceException;
import com.onetwo.userservice.common.exceptions.ResourceAlreadyExistsException;
import com.onetwo.userservice.common.jwt.JwtTokenProvider;
import com.onetwo.userservice.domain.token.RefreshToken;
import com.onetwo.userservice.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements RegisterUserUseCase, LoginUseCase, ReadUserUseCase, UpdateUserUseCase, WithdrawUserUseCase {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CreateRefreshTokenPort createRefreshTokenPort;
    private final ReadUserPort readUserPort;
    private final CreateUserPort createUserPort;
    private final UpdateUserPort updateUserPort;
    private final UserUseCaseConverter userUseCaseConverter;

    @Override
    @Transactional(readOnly = true)
    public UserIdExistCheckDto userIdExistCheck(String userId) {
        return new UserIdExistCheckDto(userIdExist(userId));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailResponseDto getUserDetailInfo(String userId) {
        User user = checkUserExistAndGetUserByUserId(userId);

        return userUseCaseConverter.userToUserDetailResponseDto(user);
    }

    @Override
    @Transactional
    public UserWithdrawResponseDto withdrawUser(WithdrawUserCommand withdrawDto, String userId) {
        if (!userId.equals(withdrawDto.getUserId())) throw new BadRequestException("withdraw can only user self");

        User user = checkUserExistAndGetUserByUserId(withdrawDto.getUserId());

        checkUserPasswordMatched(withdrawDto.getPassword(), user);

        checkUserWithdraw(user);

        user.userWithdraw();

        updateUserPort.updateUser(user);

        return userUseCaseConverter.userToUserWithdrawResponseDto(user);
    }

    @Override
    @Transactional
    public UserUpdateResponseDto updateUser(String userId, UpdateUserCommand updateUserCommand) {
        User user = checkUserExistAndGetUserByUserId(userId);

        checkUserWithdraw(user);

        user.updateUserInfo(updateUserCommand);

        updateUserPort.updateUser(user);

        return userUseCaseConverter.userToUserUpdateResponseDto(user);
    }

    @Override
    @Transactional
    public UserRegisterResponseDto registerUser(RegisterUserCommand registerUserCommand) {
        if (userIdExist(registerUserCommand.getUserId()))
            throw new ResourceAlreadyExistsException("user-id already exist");

        User newUser = User.createNewUserByCommand(registerUserCommand, passwordEncoder.encode(registerUserCommand.getPassword()));

        User savedUser = createUserPort.registerNewUser(newUser);

        // createRolePort.createNewUserRole(savedUser); Event publisher로 대체 예정

        return userUseCaseConverter.userToUserRegisterResponseDto(savedUser);
    }

    private boolean userIdExist(String userId) {
        return readUserPort.findByUserId(userId).isPresent();
    }

    @Override
    @Transactional
    public TokenResponseDto loginUser(LoginUserCommand loginUserCommand) {
        User user = checkUserExistAndGetUserByUserId(loginUserCommand.getId());

        checkUserWithdraw(user);

        checkUserPasswordMatched(loginUserCommand.getPw(), user);

        String accessToken = jwtTokenProvider.createAccessToken(user.getUserId());

        // refresh token 발급 및 저장
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUuid());
        RefreshToken token = RefreshToken.createRefreshToken(user.getUuid(), accessToken, refreshToken);

        createRefreshTokenPort.saveRefreshToken(token);

        return new TokenResponseDto(accessToken, refreshToken);
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
