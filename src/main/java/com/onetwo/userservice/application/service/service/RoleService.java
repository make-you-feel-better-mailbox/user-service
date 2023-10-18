package com.onetwo.userservice.application.service.service;

import com.onetwo.userservice.adapter.out.persistence.entity.role.PrivilegeEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RoleEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RolePrivilegeEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.UserRole;
import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;
import com.onetwo.userservice.adapter.out.persistence.repository.role.RolePrivilegeRepository;
import com.onetwo.userservice.application.port.in.role.usecase.CreateRoleUseCase;
import com.onetwo.userservice.application.port.in.role.usecase.MappingRoleUseCase;
import com.onetwo.userservice.application.port.in.role.usecase.ReadRoleUseCase;
import com.onetwo.userservice.application.port.out.role.CreateRolePort;
import com.onetwo.userservice.application.port.out.role.ReadRolePort;
import com.onetwo.userservice.domain.role.RoleNames;
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
    private final RolePrivilegeRepository rolePrivilegeRepository;

    @Override
    @Transactional
    public RoleEntity createRoleIfNotFound(RoleNames roleName) {
        Optional<RoleEntity> optionalRole = readRolePort.findRoleByRoleName(roleName);

        RoleEntity role;

        if (optionalRole.isEmpty()) {
            role = new RoleEntity(roleName);
            createRolePort.saveNewRole(role);
        } else role = optionalRole.get();

        return role;
    }

    @Override
    public void mappingRoleAndPrivilege(RoleEntity adminRole, List<PrivilegeEntity> adminPrivilege) {
        adminPrivilege.forEach(privilege -> {
            Optional<RolePrivilegeEntity> optionalRolePrivilege = readRolePort.findRolePrivilegeByRoleAndPrivilege(adminRole, privilege);
            if (optionalRolePrivilege.isEmpty()) {
                rolePrivilegeRepository.save(new RolePrivilegeEntity(adminRole, privilege));
            }
        });
    }

    @Override
    public List<RoleEntity> getRolesByUser(UserEntity user) {
        List<UserRole> userRoles = readRolePort.findUserRoleByUser(user);

        if (userRoles == null || userRoles.isEmpty()) return Collections.emptyList();

        return userRoles.stream().map(UserRole::getRole).toList();
    }
}
