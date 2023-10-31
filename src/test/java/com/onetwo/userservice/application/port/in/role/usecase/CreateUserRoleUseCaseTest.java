package com.onetwo.userservice.application.port.in.role.usecase;

import com.onetwo.userservice.adapter.out.persistence.entity.role.RoleEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.UserRoleEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;
import com.onetwo.userservice.application.port.in.role.command.CreateDefaultUserRoleCommand;
import com.onetwo.userservice.application.port.out.role.CreateUserRolePort;
import com.onetwo.userservice.application.port.out.role.ReadRolePort;
import com.onetwo.userservice.application.port.out.role.ReadUserRolePort;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.application.service.service.RoleService;
import com.onetwo.userservice.common.exceptions.NotFoundResourceException;
import com.onetwo.userservice.domain.role.Role;
import com.onetwo.userservice.domain.role.RoleNames;
import com.onetwo.userservice.domain.role.UserRole;
import com.onetwo.userservice.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CreateUserRoleUseCaseTest {

    @InjectMocks
    private RoleService createUserRoleUseCase;

    @Mock
    private ReadUserPort readUserPort;

    @Mock
    private ReadRolePort readRolePort;

    @Mock
    private ReadUserRolePort readUserRolePort;

    @Mock
    private CreateUserRolePort createUserRolePort;

    private final Long uuid = 1L;
    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final Instant birth = Instant.now();
    private final String nickname = "newNickname";
    private final String name = "tester";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";
    private final Long roleId = 1L;

    @Test
    @DisplayName("[단위][Use case] Create default user role - 성공 테스트")
    void createDefaultUserRoleUseCaseSuccessTest() {
        //given
        CreateDefaultUserRoleCommand createDefaultUserRoleCommand = new CreateDefaultUserRoleCommand(userId);

        UserEntity userEntity = new UserEntity(uuid, userId, password, birth, nickname, name, email, phoneNumber, false);
        User user = User.entityToDomain(userEntity);

        RoleEntity roleEntity = new RoleEntity(roleId, RoleNames.ROLE_USER);
        Role role = Role.entityToDomain(roleEntity);

        UserRoleEntity userRoleEntity = new UserRoleEntity(userEntity, roleEntity);
        UserRole userRole = UserRole.entityToDomain(userRoleEntity);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.of(user));
        given(readRolePort.findRoleByRoleName(any(RoleNames.class))).willReturn(Optional.of(role));
        given(readUserRolePort.findByUserAndRole(any(User.class), any(Role.class))).willReturn(Optional.of(userRole));

        //when then
        Assertions.assertDoesNotThrow(() -> createUserRoleUseCase.createDefaultUserRole(createDefaultUserRoleCommand));
    }

    @Test
    @DisplayName("[단위][Use case] Create default user role user does not exist - 실패 테스트")
    void createDefaultUserRoleUseCaseUserDoesNotExistFailTest() {
        //given
        CreateDefaultUserRoleCommand createDefaultUserRoleCommand = new CreateDefaultUserRoleCommand(userId);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.empty());

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> createUserRoleUseCase.createDefaultUserRole(createDefaultUserRoleCommand));
    }
}