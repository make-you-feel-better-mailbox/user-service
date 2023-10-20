package com.onetwo.userservice.application.service.adapter;

import com.onetwo.userservice.adapter.out.persistence.entity.role.PrivilegeEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RoleEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RolePrivilegeEntity;
import com.onetwo.userservice.adapter.out.persistence.repository.role.PrivilegeRepository;
import com.onetwo.userservice.adapter.out.persistence.repository.role.RolePrivilegeRepository;
import com.onetwo.userservice.application.port.out.role.CreatePrivilegePort;
import com.onetwo.userservice.application.port.out.role.ReadPrivilegePort;
import com.onetwo.userservice.domain.role.Privilege;
import com.onetwo.userservice.domain.role.PrivilegeNames;
import com.onetwo.userservice.domain.role.Role;
import com.onetwo.userservice.domain.role.RolePrivilege;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrivilegePersistenceAdapter implements ReadPrivilegePort, CreatePrivilegePort {

    private final PrivilegeRepository privilegeRepository;
    private final RolePrivilegeRepository rolePrivilegeRepository;

    @Override
    public Privilege saveNewPrivilege(Privilege privilege) {
        PrivilegeEntity privilegeEntity = PrivilegeEntity.domainToEntity(privilege);

        PrivilegeEntity savedPrivilegeEntity = privilegeRepository.save(privilegeEntity);

        return Privilege.entityToDomain(savedPrivilegeEntity);
    }

    @Override
    public Optional<Privilege> findPrivilegeByPrivilegeName(PrivilegeNames privilegeName) {
        Optional<PrivilegeEntity> optionalPrivilegeEntity = privilegeRepository.findByPrivilegeName(privilegeName);

        if (optionalPrivilegeEntity.isPresent()) {
            Privilege privilege = Privilege.entityToDomain(optionalPrivilegeEntity.get());
            return Optional.of(privilege);
        }

        return Optional.empty();
    }

    @Override
    public List<RolePrivilege> findRolePrivilegeByRole(Role role) {
        RoleEntity roleEntity = RoleEntity.domainToEntity(role);

        List<RolePrivilegeEntity> privilegeEntityList = rolePrivilegeRepository.findByRole(roleEntity);

        if (privilegeEntityList == null) return Collections.emptyList();

        return privilegeEntityList.stream()
                .map(RolePrivilege::entityToDomain).toList();
    }
}
