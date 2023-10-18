package com.onetwo.userservice.application.service.adapter;

import com.onetwo.userservice.adapter.out.persistence.entity.role.PrivilegeEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RoleEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RolePrivilegeEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.UserRole;
import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;
import com.onetwo.userservice.adapter.out.persistence.repository.role.RolePrivilegeRepository;
import com.onetwo.userservice.adapter.out.persistence.repository.role.RoleRepository;
import com.onetwo.userservice.adapter.out.persistence.repository.role.UserRoleRepository;
import com.onetwo.userservice.application.port.out.role.CreateRolePort;
import com.onetwo.userservice.application.port.out.role.ReadRolePort;
import com.onetwo.userservice.domain.role.RoleNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolePersistenceAdapter implements CreateRolePort, ReadRolePort {

    private final RoleRepository roleRepository;
    private final RolePrivilegeRepository rolePrivilegeRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public void saveNewRole(RoleEntity role) {
        roleRepository.save(role);
    }

    @Override
    public Optional<RoleEntity> findRoleByRoleName(RoleNames roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public Optional<RolePrivilegeEntity> findRolePrivilegeByRoleAndPrivilege(RoleEntity adminRole, PrivilegeEntity privilege) {
        return rolePrivilegeRepository.findByRoleAndPrivilege(adminRole, privilege);
    }

    @Override
    public List<UserRole> findUserRoleByUser(UserEntity user) {
        return userRoleRepository.findByUser(user);
    }
}
