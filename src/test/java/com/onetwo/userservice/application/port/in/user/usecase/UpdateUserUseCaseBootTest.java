package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.command.UpdateUserCommand;
import com.onetwo.userservice.application.port.out.user.CreateUserPort;
import com.onetwo.userservice.application.port.out.user.UpdateUserPort;
import com.onetwo.userservice.application.service.response.UserUpdateResponseDto;
import com.onetwo.userservice.common.exceptions.BadRequestException;
import com.onetwo.userservice.common.exceptions.NotFoundResourceException;
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
class UpdateUserUseCaseBootTest {

    @Autowired
    private UpdateUserUseCase updateUserUseCase;

    @Autowired
    private CreateUserPort createUserPort;

    @Autowired
    private UpdateUserPort updateUserPort;

    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final Instant birth = Instant.now();
    private final String nickname = "newNickname";
    private final String name = "tester";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";

    @Test
    @DisplayName("[통합][Use Case] 회원 수정 - 성공 테스트")
    void updateUserUseCaseSuccessTest() {
        //given
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);
        User user = User.createNewUserByCommand(registerUserCommand, password);

        createUserPort.registerNewUser(user);

        UpdateUserCommand updateUserCommand = new UpdateUserCommand(userId, birth, nickname, name, email, phoneNumber);

        //when
        UserUpdateResponseDto result = updateUserUseCase.updateUser(updateUserCommand);

        //then
        Assertions.assertSame(result.userId(), userId);
    }

    @Test
    @DisplayName("[통합][Use Case] 회원 수정 user does not exist - 실패 테스트")
    void updateUserUseCaseUserDoesNotExistFailTest() {
        //given
        UpdateUserCommand updateUserCommand = new UpdateUserCommand(userId, birth, nickname, name, email, phoneNumber);

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> updateUserUseCase.updateUser(updateUserCommand));
    }

    @Test
    @DisplayName("[통합][Use Case] 회원 수정 user withdrew - 실패 테스트")
    void updateUserUseCaseUserWithdrewFailTest() {
        //given
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);
        User user = User.createNewUserByCommand(registerUserCommand, password);

        User savedUser = createUserPort.registerNewUser(user);
        savedUser.userWithdraw();
        updateUserPort.updateUser(savedUser);

        UpdateUserCommand updateUserCommand = new UpdateUserCommand(userId, birth, nickname, name, email, phoneNumber);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> updateUserUseCase.updateUser(updateUserCommand));
    }
}
