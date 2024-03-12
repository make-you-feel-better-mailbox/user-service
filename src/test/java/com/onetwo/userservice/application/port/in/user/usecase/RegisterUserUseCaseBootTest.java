package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.response.UserRegisterResponseDto;
import com.onetwo.userservice.application.port.out.user.RegisterUserPort;
import com.onetwo.userservice.common.exceptions.ResourceAlreadyExistsException;
import com.onetwo.userservice.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class RegisterUserUseCaseBootTest {

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    @Autowired
    private RegisterUserPort registerUserPort;

    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final String nickname = "newNickname";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";
    private final boolean oauth = false;
    private final String registrationId = null;

    private final RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, nickname, email, phoneNumber, oauth, registrationId);

    @Test
    @DisplayName("[통합][Use Case] 회원 회원가입 - 성공 테스트")
    void registerUserUseCaseSuccessTest() {
        //given when
        UserRegisterResponseDto result = registerUserUseCase.registerUser(registerUserCommand);

        //then
        Assertions.assertSame(result.userId(), userId);
    }

    @Test
    @DisplayName("[통합][Use Case] 회원 회원가입 User id already exist - 실패 테스트")
    void registerUserUseCaseUserIdExistFailTest() {
        //given
        User user = User.createNewUserByCommand(registerUserCommand, password);

        registerUserPort.registerNewUser(user);

        //when then
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> registerUserUseCase.registerUser(registerUserCommand));
    }
}
