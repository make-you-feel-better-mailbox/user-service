package com.onetwo.userservice.adapter.in.event;

import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.usecase.RegisterUserUseCase;
import com.onetwo.userservice.application.port.out.role.ReadRolePort;
import com.onetwo.userservice.application.port.out.role.ReadUserRolePort;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.common.exceptions.NotFoundResourceException;
import com.onetwo.userservice.domain.role.Role;
import com.onetwo.userservice.domain.role.RoleNames;
import com.onetwo.userservice.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class UserRegisterEventListenerTest {

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ReadUserRolePort readUserRolePort;

    @Autowired
    private ReadUserPort readUserPort;

    @Autowired
    private ReadRolePort readRolePort;

    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final String nickname = "newNickname";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";
    private final boolean oauth = false;
    private final String registrationId = null;

    @Test
    @Transactional
    @DisplayName("[통합][Event Adapter] Create default user role when register user - 성공 테스트")
    void createDefaultUserRoleSuccessTest() {
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, nickname, email, phoneNumber, oauth, registrationId);

        registerUserUseCase.registerUser(registerUserCommand);

        Assertions.assertDoesNotThrow(() -> {
            User user = readUserPort.findByUserId(userId).orElseThrow(() -> new NotFoundResourceException("user does not exist"));
            Role role = readRolePort.findRoleByRoleName(RoleNames.ROLE_USER).orElseThrow(() -> new NotFoundResourceException("role does not exist"));
            Assertions.assertTrue(readUserRolePort.findByUserAndRole(user, role).isPresent());
        });
    }
}