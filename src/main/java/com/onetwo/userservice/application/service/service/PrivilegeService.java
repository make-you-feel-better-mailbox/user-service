package com.onetwo.userservice.application.service.service;

import com.onetwo.userservice.application.port.in.role.usecase.CreatePrivilegeUseCase;
import com.onetwo.userservice.application.port.in.role.usecase.ReadPrivilegeUseCase;
import com.onetwo.userservice.application.port.out.role.CreatePrivilegePort;
import com.onetwo.userservice.application.port.out.role.ReadPrivilegePort;
import com.onetwo.userservice.domain.role.Privilege;
import com.onetwo.userservice.domain.role.PrivilegeNames;
import com.onetwo.userservice.domain.role.Role;
import com.onetwo.userservice.domain.role.RolePrivilege;
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
    public Privilege createPrivilegeIfNotFound(PrivilegeNames privilegeName) {
        Optional<Privilege> optionalPrivilege = readPrivilegePort.findPrivilegeByPrivilegeName(privilegeName);

        if (optionalPrivilege.isEmpty()) {
            Privilege newPrivilege = Privilege.createPrivilege(privilegeName);
            return createPrivilegePort.saveNewPrivilege(newPrivilege);
        } else return optionalPrivilege.get();
    }

    @Override
    public List<Privilege> getPrivilegeByRole(Role role) {
        List<RolePrivilege> rolePrivileges = readPrivilegePort.findRolePrivilegeByRole(role);

        if (rolePrivileges == null || rolePrivileges.isEmpty()) return Collections.emptyList();

        return rolePrivileges.stream().map(RolePrivilege::getPrivilege).toList();
    }
}
