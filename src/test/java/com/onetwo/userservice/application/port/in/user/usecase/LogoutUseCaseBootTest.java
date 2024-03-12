package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.LogoutUserCommand;
import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.response.LogoutResponseDto;
import com.onetwo.userservice.application.port.out.user.RegisterUserPort;
import com.onetwo.userservice.application.port.out.user.UpdateUserPort;
import com.onetwo.userservice.common.exceptions.NotFoundResourceException;
import com.onetwo.userservice.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class LogoutUseCaseBootTest {

    @Autowired
    private LogoutUseCase logoutUseCase;

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
    @DisplayName("[통합][Use Case] 회원 로그아웃 - 성공 테스트")
    void logoutUserUseCaseSuccessTest() {
        //given
        LogoutUserCommand logoutUserCommand = new LogoutUserCommand(userId);
        User user = User.createNewUserByCommand(registerUserCommand, passwordEncoder.encode(password));

        registerUserPort.registerNewUser(user);

        //when
        LogoutResponseDto result = logoutUseCase.logoutUser(logoutUserCommand);

        Assertions.assertTrue(result.isLogoutSuccess());
    }

    @Test
    @DisplayName("[통합][Use Case] 회원 로그아웃 User does not exist - 실패 테스트")
    void logoutUserUseCaseUserDoesNotExistFailTest() {
        //given
        LogoutUserCommand logoutUserCommand = new LogoutUserCommand(userId);

        //when
        Assertions.assertThrows(NotFoundResourceException.class, () -> logoutUseCase.logoutUser(logoutUserCommand));
    }
}
