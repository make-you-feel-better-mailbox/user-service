package com.onetwo.userservice.application.service.adapter;

import com.onetwo.userservice.adapter.out.persistence.entity.role.PrivilegeEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RoleEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RolePrivilegeEntity;
import com.onetwo.userservice.adapter.out.persistence.repository.role.PrivilegeRepository;
import com.onetwo.userservice.adapter.out.persistence.repository.role.RolePrivilegeRepository;
import com.onetwo.userservice.application.port.out.role.CreatePrivilegePort;
import com.onetwo.userservice.application.port.out.role.ReadPrivilegePort;
import com.onetwo.userservice.domain.role.PrivilegeNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrivilegePersistenceAdapter implements ReadPrivilegePort, CreatePrivilegePort {

    private final PrivilegeRepository privilegeRepository;
    private final RolePrivilegeRepository rolePrivilegeRepository;

    @Override
    public void saveNewPrivilege(PrivilegeEntity privilege) {
        privilegeRepository.save(privilege);
    }

    @Override
    public Optional<PrivilegeEntity> findPrivilegeByPrivilegeName(PrivilegeNames privilegeName) {
        return privilegeRepository.findByPrivilegeName(privilegeName);
    }

    @Override
    public List<RolePrivilegeEntity> findRolePrivilegeByRole(RoleEntity role) {
        return rolePrivilegeRepository.findByRole(role);
    }
}
