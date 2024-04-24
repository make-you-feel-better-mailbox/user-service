package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.LoginUserCommand;
import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.response.TokenResponseDto;
import com.onetwo.userservice.application.port.out.user.RegisterUserPort;
import com.onetwo.userservice.application.port.out.user.UpdateUserPort;
import com.onetwo.userservice.common.exceptions.BadRequestException;
import com.onetwo.userservice.common.exceptions.NotFoundResourceException;
import com.onetwo.userservice.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = "spring.profiles.active=test")
@Transactional
class LoginUseCaseBootTest {

    @Autowired
    private LoginUseCase loginUseCase;

    @Autowired
    private RegisterUserPort registerUserPort;

    @Autowired
    private UpdateUserPort updateUserPort;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final String nickname = "newNickname";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";
    private final boolean oauth = false;
    private final String registrationId = null;

    private final RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, nickname, email, phoneNumber, oauth, registrationId);


    @Test
    @DisplayName("[통합][Use Case] 회원 로그인 - 성공 테스트")
    void loginUserUseCaseSuccessBootTest() {
        //given
        LoginUserCommand loginUserCommand = new LoginUserCommand(userId, password);

        User user = User.createNewUserByCommand(registerUserCommand, passwordEncoder.encode(password));

        registerUserPort.registerNewUser(user);

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

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> loginUseCase.loginUser(loginUserCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 로그인 User already withdrew - 실패 테스트")
    void loginUserUseCaseUserAlreadyWithdrewFailTest() {
        //given
        LoginUserCommand loginUserCommand = new LoginUserCommand(userId, password);

        User user = User.createNewUserByCommand(registerUserCommand, password);

        User savedUser = registerUserPort.registerNewUser(user);
        savedUser.userWithdraw();
        updateUserPort.updateUser(savedUser);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> loginUseCase.loginUser(loginUserCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 로그인 User password not matched - 실패 테스트")
    void loginUserUseCasePasswordNotMatchedFailTest() {
        //given
        String incorrectPassword = "incorrectPassword";
        LoginUserCommand loginUserCommand = new LoginUserCommand(userId, incorrectPassword);

        User user = User.createNewUserByCommand(registerUserCommand, password);

        registerUserPort.registerNewUser(user);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> loginUseCase.loginUser(loginUserCommand));
    }
}
