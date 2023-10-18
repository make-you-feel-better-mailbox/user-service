package com.onetwo.userservice.application.service.service;

import com.onetwo.userservice.adapter.out.persistence.entity.role.PrivilegeEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RoleEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RolePrivilegeEntity;
import com.onetwo.userservice.application.port.in.role.usecase.CreatePrivilegeUseCase;
import com.onetwo.userservice.application.port.in.role.usecase.ReadPrivilegeUseCase;
import com.onetwo.userservice.application.port.out.role.CreatePrivilegePort;
import com.onetwo.userservice.application.port.out.role.ReadPrivilegePort;
import com.onetwo.userservice.domain.role.PrivilegeNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrivilegeService implements CreatePrivilegeUseCase, ReadPrivilegeUseCase {

    private final ReadPrivilegePort readPrivilegePort;
    private final CreatePrivilegePort createPrivilegePort;

    @Override
    @Transactional
    public PrivilegeEntity createPrivilegeIfNotFound(PrivilegeNames privilegeName) {
        Optional<PrivilegeEntity> optionalPrivilege = readPrivilegePort.findPrivilegeByPrivilegeName(privilegeName);

        PrivilegeEntity privilege;

        if (optionalPrivilege.isEmpty()) {
            privilege = new PrivilegeEntity(privilegeName);
            createPrivilegePort.saveNewPrivilege(privilege);
        } else privilege = optionalPrivilege.get();

        return privilege;
    }

    @Override
    public List<PrivilegeEntity> getPrivilegeByRole(RoleEntity role) {
        List<RolePrivilegeEntity> rolePrivileges = readPrivilegePort.findRolePrivilegeByRole(role);

        if (rolePrivileges == null || rolePrivileges.isEmpty()) return Collections.emptyList();

        return rolePrivileges.stream().map(RolePrivilegeEntity::getPrivilege).toList();
    }
}
