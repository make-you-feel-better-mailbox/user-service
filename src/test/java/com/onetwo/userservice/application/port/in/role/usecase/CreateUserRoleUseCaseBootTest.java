package com.onetwo.userservice.application.port.in.role.usecase;

import com.onetwo.userservice.application.port.in.role.command.CreateDefaultUserRoleCommand;
import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.out.user.CreateUserPort;
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
class CreateUserRoleUseCaseBootTest {

    @Autowired
    private CreateUserRoleUseCase createUserRoleUseCase;

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
    @DisplayName("[통합][Use case] Create default user role - 성공 테스트")
    void createDefaultUserRoleUseCaseSuccessTest() {
        //given
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);
        User user = User.createNewUserByCommand(registerUserCommand, password);

        createUserPort.registerNewUser(user);

        CreateDefaultUserRoleCommand createDefaultUserRoleCommand = new CreateDefaultUserRoleCommand(userId);

        //when then
        Assertions.assertDoesNotThrow(() -> createUserRoleUseCase.createDefaultUserRole(createDefaultUserRoleCommand));
    }

    @Test
    @DisplayName("[통합][Use case] Create default user role user does not exist - 실패 테스트")
    void createDefaultUserRoleUseCaseUserDoesNotExistFailTest() {
        //given
        CreateDefaultUserRoleCommand createDefaultUserRoleCommand = new CreateDefaultUserRoleCommand(userId);

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> createUserRoleUseCase.createDefaultUserRole(createDefaultUserRoleCommand));
    }
}
