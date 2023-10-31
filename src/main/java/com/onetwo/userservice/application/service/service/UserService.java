package com.onetwo.userservice.application.service.service;

import com.onetwo.userservice.application.port.in.user.command.*;
import com.onetwo.userservice.application.port.in.user.response.*;
import com.onetwo.userservice.application.port.in.user.usecase.*;
import com.onetwo.userservice.application.port.out.event.UserRegisterEventPublisherPort;
import com.onetwo.userservice.application.port.out.token.CreateRefreshTokenPort;
import com.onetwo.userservice.application.port.out.token.DeleteRefreshTokenPort;
import com.onetwo.userservice.application.port.out.token.ReadRefreshTokenPort;
import com.onetwo.userservice.application.port.out.user.CreateUserPort;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.application.port.out.user.UpdateUserPort;
import com.onetwo.userservice.application.service.converter.TokenUseCaseConverter;
import com.onetwo.userservice.application.service.converter.UserUseCaseConverter;
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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements RegisterUserUseCase, LoginUseCase, ReadUserUseCase, UpdateUserUseCase, WithdrawUserUseCase, LogoutUseCase {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CreateRefreshTokenPort createRefreshTokenPort;
    private final DeleteRefreshTokenPort deleteRefreshTokenPort;
    private final ReadRefreshTokenPort readRefreshTokenPort;
    private final ReadUserPort readUserPort;
    private final CreateUserPort createUserPort;
    private final UpdateUserPort updateUserPort;
    private final UserUseCaseConverter userUseCaseConverter;
    private final TokenUseCaseConverter tokenUseCaseConverter;
    private final UserRegisterEventPublisherPort userRegisterEventPublisherPort;


    /**
     * User id exist check use case,
     * user id is unique, so check before register user
     *
     * @param userId userId
     * @return Boolean about user id already exist
     */
    @Override
    @Transactional(readOnly = true)
    public UserIdExistCheckDto userIdExistCheck(String userId) {
        Boolean isUserIdExist = isUserIdExist(userId);
        return userUseCaseConverter.toUserIdExistCheckDto(isUserIdExist);
    }

    /**
     * Get about user detail information use case
     *
     * @param userId userId
     * @return Detail Information about User
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetailResponseDto getUserDetailInfo(String userId) {
        User user = checkUserExistAndGetUserByUserId(userId);
        return userUseCaseConverter.userToUserDetailResponseDto(user);
    }

    /**
     * Withdraw user use case,
     * Check user exist and request user is same with withdraw user,
     * user can withdraw only him self
     * if withdraw success user state ganna change true
     *
     * @param withdrawDto request userId and requester Id
     * @return Boolean about withdraw success
     */
    @Override
    @Transactional
    public UserWithdrawResponseDto withdrawUser(WithdrawUserCommand withdrawDto) {
        if (isRequestUserIdDifferentWithUserId(withdrawDto))
            throw new BadRequestException("withdraw can only user self");

        User user = checkUserExistAndGetUserByUserId(withdrawDto.getUserId());

        checkUserPasswordMatched(withdrawDto.getPassword(), user);

        checkUserWithdraw(user);

        user.userWithdraw();

        updateUserPort.updateUser(user);

        return userUseCaseConverter.userToUserWithdrawResponseDto(user);
    }

    /**
     * Check is request user id different with user id
     *
     * @param withdrawUserCommand request withdraw userid and requester id
     * @return Boolean about is different
     */
    private boolean isRequestUserIdDifferentWithUserId(WithdrawUserCommand withdrawUserCommand) {
        return !withdrawUserCommand.getRequestUserId().equals(withdrawUserCommand.getUserId());
    }

    /**
     * Update user use case,
     * Check user exist and update about user information
     *
     * @param updateUserCommand update user information with userId
     * @return Updated user information
     */
    @Override
    @Transactional
    public UserUpdateResponseDto updateUser(UpdateUserCommand updateUserCommand) {
        User user = checkUserExistAndGetUserByUserId(updateUserCommand.getUserId());

        checkUserWithdraw(user);

        user.updateUserInfo(updateUserCommand);

        updateUserPort.updateUser(user);

        return userUseCaseConverter.userToUserUpdateResponseDto(user);
    }

    /**
     * Update user password Use case,
     * Check user exist and check current password, new password, new password check
     *
     * @param updateUserPasswordCommand request update password information
     * @return Boolean about update success
     */
    @Override
    @Transactional
    public UserUpdatePasswordResponseDto updatePassword(UpdateUserPasswordCommand updateUserPasswordCommand) {
        User user = checkUserExistAndGetUserByUserId(updateUserPasswordCommand.getUserId());

        checkUserWithdraw(user);

        checkUserPasswordMatched(updateUserPasswordCommand.getCurrentPassword(), user);

        if (isNewPasswordNotEqualsWithNewPasswordCheck(updateUserPasswordCommand))
            throw new BadRequestException("new password does not same with new password check");

        if (isPasswordMatches(updateUserPasswordCommand.getNewPassword(), user))
            throw new BadRequestException("new password same with current password");

        user.updateEncodePassword(passwordEncoder.encode(updateUserPasswordCommand.getNewPassword()));

        updateUserPort.updateUser(user);

        boolean userPasswordUpdated = !isPasswordMatches(updateUserPasswordCommand.getCurrentPassword(), user);

        return userUseCaseConverter.toUserUpdatePasswordResponseDto(userPasswordUpdated);
    }

    /**
     * check new password is matched with new password check
     *
     * @param updateUserPasswordCommand request update password information
     * @return Boolean about is equals
     */
    private boolean isNewPasswordNotEqualsWithNewPasswordCheck(UpdateUserPasswordCommand updateUserPasswordCommand) {
        return !updateUserPasswordCommand.getNewPassword().equals(updateUserPasswordCommand.getNewPasswordCheck());
    }

    /**
     * Register user use case,
     * Check user id already exist, if exist throw exception.
     * Also encode password and register user
     *
     * @param registerUserCommand request register user information
     * @return Register Succeed User Id
     */
    @Override
    @Transactional
    public UserRegisterResponseDto registerUser(RegisterUserCommand registerUserCommand) {
        if (isUserIdExist(registerUserCommand.getUserId()))
            throw new ResourceAlreadyExistsException("user-id already exist");

        User newUser = User.createNewUserByCommand(registerUserCommand, passwordEncoder.encode(registerUserCommand.getPassword()));

        User savedUser = createUserPort.registerNewUser(newUser);

        userRegisterEventPublisherPort.publishEvent(savedUser);

        return userUseCaseConverter.userToUserRegisterResponseDto(savedUser);
    }

    /**
     * Check user id exist in persistence
     *
     * @param userId check user id
     * @return boolean about user id exist in persistence
     */
    private boolean isUserIdExist(String userId) {
        return readUserPort.findByUserId(userId).isPresent();
    }

    /**
     * User login use case,
     * Check user exist and state
     * also check id and password matches
     * if pass all check then issue Access token and Refresh token
     * also save Refresh token to cache and return Refresh token and Access token
     *
     * @param loginUserCommand userId and password
     * @return Refresh Token And Access Token
     */
    @Override
    @Transactional
    public TokenResponseDto loginUser(LoginUserCommand loginUserCommand) {
        User user = checkUserExistAndGetUserByUserId(loginUserCommand.getId());

        checkUserWithdraw(user);

        checkUserPasswordMatched(loginUserCommand.getPassword(), user);

        String accessToken = jwtTokenProvider.createAccessToken(user.getUserId());

        // refresh token 발급 및 저장
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUuid());
        RefreshToken token = RefreshToken.createRefreshToken(user.getUuid(), accessToken, refreshToken);

        createRefreshTokenPort.saveRefreshToken(token);

        return tokenUseCaseConverter.tokenToTokenResponseDto(token);
    }

    /**
     * User logout use case,
     * Check user exist and delete if Refresh Token exist
     * After that check Refresh token exist in cache
     * if does not exist, it's mean logout succeed
     *
     * @param logoutUserCommand userId
     * @return Boolean about logout success
     */
    @Override
    @Transactional
    public LogoutResponseDto logoutUser(LogoutUserCommand logoutUserCommand) {
        User user = checkUserExistAndGetUserByUserId(logoutUserCommand.getUserId());

        Optional<RefreshToken> refreshToken = readRefreshTokenPort.findRefreshTokenById(user.getUuid());

        refreshToken.ifPresent(deleteRefreshTokenPort::deleteRefreshToken);

        boolean refreshTokenNotExist = readRefreshTokenPort.findRefreshTokenById(user.getUuid()).isEmpty();

        return tokenUseCaseConverter.resultToLogoutResponseDto(refreshTokenNotExist);
    }

    /**
     * Check user withdraw. if user withdrew, then throw exception
     *
     * @param user withdraw check user
     */
    private void checkUserWithdraw(User user) {
        if (user.isUserWithdraw()) throw new BadRequestException("User already withdraw");
    }

    /**
     * Check user password is matched and if is not throw exception
     *
     * @param requestPassword request password
     * @param user            user
     */
    private void checkUserPasswordMatched(String requestPassword, User user) {
        if (!isPasswordMatches(requestPassword, user))
            throw new BadRequestException("Password does not match");
    }

    /**
     * check user password is matched
     *
     * @param requestPassword request password
     * @param user            user
     * @return boolean about password matches
     */
    private boolean isPasswordMatches(String requestPassword, User user) {
        return passwordEncoder.matches(requestPassword, user.getPassword());
    }

    /**
     * Check user exist in persistence.
     * if exist then return user, if is not exist then throw exception
     *
     * @param userId check and get user id
     * @return return user when user exist and if not throw exception
     */
    private User checkUserExistAndGetUserByUserId(String userId) {
        return readUserPort.findByUserId(userId).orElseThrow(() -> new NotFoundResourceException("user-id does not exist"));
    }
}
