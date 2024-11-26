package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;
import com.onetwo.userservice.application.port.in.user.command.LoginUserCommand;
import com.onetwo.userservice.application.port.in.user.response.TokenResponseDto;
import com.onetwo.userservice.application.port.out.token.CreateRefreshTokenPort;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.application.service.converter.TokenUseCaseConverter;
import com.onetwo.userservice.application.service.service.UserService;
import com.onetwo.userservice.common.exceptions.BadRequestException;
import com.onetwo.userservice.common.exceptions.NotFoundResourceException;
import com.onetwo.userservice.common.jwt.JwtTokenProvider;
import com.onetwo.userservice.domain.token.RefreshToken;
import com.onetwo.userservice.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @InjectMocks
    private UserService loginUseCase;

    @Mock
    private ReadUserPort readUserPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private CreateRefreshTokenPort createRefreshTokenPort;

    @Mock
    private TokenUseCaseConverter tokenUseCaseConverter;

    private final Long uuid = 1L;
    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final String nickname = "newNickname";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";
    private final boolean oauth = false;
    private final String registrationId = null;
    private final String profileImageEndPoint = "/assets/images/avatars/avatar-2.jpg";

    private final UserEntity userEntity = new UserEntity(uuid, userId, password, nickname, email, phoneNumber, profileImageEndPoint, oauth, registrationId, false);

    @Test
    @DisplayName("[단위][Use Case] 회원 로그인 - 성공 테스트")
    void loginUserUseCaseSuccessTest() {
        //given
        LoginUserCommand loginUserCommand = new LoginUserCommand(userId, password);

        User user = User.entityToDomain(userEntity);

        TokenResponseDto tokenResponseDto = new TokenResponseDto("created-access-token", "created-refresh-token");

        given(readUserPort.findByUserId(userId)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        given(jwtTokenProvider.createAccessToken(userId)).willReturn("created-access-token");
        given(jwtTokenProvider.createRefreshToken(anyLong())).willReturn("created-refresh-token");
        given(tokenUseCaseConverter.tokenToTokenResponseDto(any(RefreshToken.class))).willReturn(tokenResponseDto);

        //when
        TokenResponseDto result = loginUseCase.loginUser(loginUserCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.accessToken());
        Assertions.assertNotNull(result.refreshToken());
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 로그인 User does not exist - 실패 테스트")
    void loginUserUseCaseUserDoesNotExistFailTest() {
        //given
        LoginUserCommand loginUserCommand = new LoginUserCommand(userId, password);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.empty());

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> loginUseCase.loginUser(loginUserCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 로그인 User already withdrew - 실패 테스트")
    void loginUserUseCaseUserAlreadyWithdrewFailTest() {
        //given
        LoginUserCommand loginUserCommand = new LoginUserCommand(userId, password);
        User user = User.entityToDomain(userEntity);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.of(user));

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> loginUseCase.loginUser(loginUserCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 로그인 User password not matched - 실패 테스트")
    void loginUserUseCasePasswordNotMatchedFailTest() {
        //given
        LoginUserCommand loginUserCommand = new LoginUserCommand(userId, password);
        User user = User.entityToDomain(userEntity);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> loginUseCase.loginUser(loginUserCommand));
    }
}