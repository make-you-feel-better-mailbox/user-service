package com.onetwo.userservice.application.service.service;

import com.onetwo.userservice.application.port.in.role.command.CreateDefaultUserRoleCommand;
import com.onetwo.userservice.application.port.in.role.usecase.CreateRoleUseCase;
import com.onetwo.userservice.application.port.in.role.usecase.CreateUserRoleUseCase;
import com.onetwo.userservice.application.port.in.role.usecase.MappingRoleUseCase;
import com.onetwo.userservice.application.port.in.role.usecase.ReadRoleUseCase;
import com.onetwo.userservice.application.port.out.role.CreateRolePort;
import com.onetwo.userservice.application.port.out.role.CreateUserRolePort;
import com.onetwo.userservice.application.port.out.role.ReadRolePort;
import com.onetwo.userservice.application.port.out.role.ReadUserRolePort;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.common.exceptions.NotFoundResourceException;
import com.onetwo.userservice.domain.role.*;
import com.onetwo.userservice.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements ReadRoleUseCase, CreateRoleUseCase, MappingRoleUseCase, CreateUserRoleUseCase {

    private final ReadRolePort readRolePort;
    private final CreateRolePort createRolePort;
    private final ReadUserPort readUserPort;
    private final ReadUserRolePort readUserRolePort;
    private final CreateUserRolePort createUserRolePort;

    /**
     * Create role if not found use case,
     * Save role if not found on persistence
     *
     * @param roleName roleName
     * @return saved role
     */
    @Override
    @Transactional
    public Role createRoleIfNotFound(RoleNames roleName) {
        Optional<Role> optionalRole = readRolePort.findRoleByRoleName(roleName);

        if (optionalRole.isEmpty()) {
            Role newRole = Role.createRoleByRoleName(roleName);
            return createRolePort.saveNewRole(newRole);
        } else return optionalRole.get();
    }

    /**
     * Mapping role and privilege use case,
     * on persistence 1:M (one-to-many) mapping
     *
     * @param role       role
     * @param privileges List of privilege
     */
    @Override
    @Transactional
    public void mappingRoleAndPrivilege(Role role, List<Privilege> privileges) {
        privileges.forEach(privilege -> {
            Optional<RolePrivilege> optionalRolePrivilege = readRolePort.findRolePrivilegeByRoleAndPrivilege(role, privilege);
            if (optionalRolePrivilege.isEmpty()) {
                createRolePort.saveNewRolePrivilege(RolePrivilege.createRolePrivilege(role, privilege));
            }
        });
    }

    /**
     * Get roles by user use case,
     * Find user's roles on persistence
     *
     * @param user user domain
     * @return List of user's roles
     */
    @Override
    @Transactional(readOnly = true)
    public List<Role> getRolesByUser(User user) {
        List<UserRole> userRoles = readRolePort.findUserRoleByUser(user);

        if (userRoles == null || userRoles.isEmpty()) return Collections.emptyList();

        return userRoles.stream().map(UserRole::getRole).toList();
    }

    /**
     * Create default user role
     *
     * @param createDefaultUserRoleCommand uuid
     */
    @Override
    @Transactional
    public void createDefaultUserRole(CreateDefaultUserRoleCommand createDefaultUserRoleCommand) {
        User user = readUserPort.findByUserId(createDefaultUserRoleCommand.userId()).orElseThrow(() -> new NotFoundResourceException("user does not exist"));
        Optional<Role> optionalRole = readRolePort.findRoleByRoleName(RoleNames.ROLE_USER);

        Role role;
        if (optionalRole.isEmpty()) role = createRoleIfNotFound(RoleNames.ROLE_USER);
        else role = optionalRole.get();

        Optional<UserRole> optionalUserRole = readUserRolePort.findByUserAndRole(user, role);

        if (optionalUserRole.isEmpty()) createUserRolePort.saveNewUserRole(UserRole.createUserRole(user, role));
    }
}
