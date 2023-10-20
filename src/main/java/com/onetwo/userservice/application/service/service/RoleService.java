package com.onetwo.userservice.application.service.service;

import com.onetwo.userservice.application.port.in.role.usecase.CreateRoleUseCase;
import com.onetwo.userservice.application.port.in.role.usecase.MappingRoleUseCase;
import com.onetwo.userservice.application.port.in.role.usecase.ReadRoleUseCase;
import com.onetwo.userservice.application.port.out.role.CreateRolePort;
import com.onetwo.userservice.application.port.out.role.ReadRolePort;
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
public class RoleService implements ReadRoleUseCase, CreateRoleUseCase, MappingRoleUseCase {

    private final ReadRolePort readRolePort;
    private final CreateRolePort createRolePort;

    @Override
    @Transactional
    public Role createRoleIfNotFound(RoleNames roleName) {
        Optional<Role> optionalRole = readRolePort.findRoleByRoleName(roleName);

        if (optionalRole.isEmpty()) {
            Role newRole = Role.createRoleByRoleName(roleName);
            return createRolePort.saveNewRole(newRole);
        } else return optionalRole.get();
    }

    @Override
    public void mappingRoleAndPrivilege(Role role, List<Privilege> privileges) {
        privileges.forEach(privilege -> {
            Optional<RolePrivilege> optionalRolePrivilege = readRolePort.findRolePrivilegeByRoleAndPrivilege(role, privilege);
            if (optionalRolePrivilege.isEmpty()) {
                createRolePort.saveNewRolePrivilege(RolePrivilege.createRolePrivilege(role, privilege));
            }
        });
    }

    @Override
    public List<Role> getRolesByUser(User user) {
        List<UserRole> userRoles = readRolePort.findUserRoleByUser(user);

        if (userRoles == null || userRoles.isEmpty()) return Collections.emptyList();

        return userRoles.stream().map(UserRole::getRole).toList();
    }
}
