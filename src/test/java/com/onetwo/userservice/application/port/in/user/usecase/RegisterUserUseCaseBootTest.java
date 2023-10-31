package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.out.user.CreateUserPort;
import com.onetwo.userservice.application.port.in.user.response.UserRegisterResponseDto;
import com.onetwo.userservice.common.exceptions.ResourceAlreadyExistsException;
import com.onetwo.userservice.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@SpringBootTest
@Transactional
class RegisterUserUseCaseBootTest {

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    @Autowired
    private CreateUserPort createUserPort;

    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final Instant birth = Instant.now();
    private final String nickname = "newNickname";
    private final String name = "tester";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";

    @Test
    @DisplayName("[통합][Use Case] 회원 회원가입 - 성공 테스트")
    void registerUserUseCaseSuccessTest() {
        //given
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);

        //when
        UserRegisterResponseDto result = registerUserUseCase.registerUser(registerUserCommand);

        //then
        Assertions.assertSame(result.userId(), userId);
    }

    @Test
    @DisplayName("[통합][Use Case] 회원 회원가입 User id already exist - 실패 테스트")
    void registerUserUseCaseUserIdExistFailTest() {
        //given
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);

        User user = User.createNewUserByCommand(registerUserCommand, password);

        createUserPort.registerNewUser(user);

        //when then
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> registerUserUseCase.registerUser(registerUserCommand));
    }
}
