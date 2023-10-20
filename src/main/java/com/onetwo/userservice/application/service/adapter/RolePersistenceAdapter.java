package com.onetwo.userservice.application.service.adapter;

import com.onetwo.userservice.adapter.out.persistence.entity.role.PrivilegeEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RoleEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RolePrivilegeEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.UserRoleEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;
import com.onetwo.userservice.adapter.out.persistence.repository.role.RolePrivilegeRepository;
import com.onetwo.userservice.adapter.out.persistence.repository.role.RoleRepository;
import com.onetwo.userservice.adapter.out.persistence.repository.role.UserRoleRepository;
import com.onetwo.userservice.application.port.out.role.CreateRolePort;
import com.onetwo.userservice.application.port.out.role.ReadRolePort;
import com.onetwo.userservice.domain.role.*;
import com.onetwo.userservice.domain.user.User;
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
    public Role saveNewRole(Role role) {
        RoleEntity roleEntity = RoleEntity.domainToEntity(role);

        RoleEntity savedRoleEntity = roleRepository.save(roleEntity);

        return Role.entityToDomain(savedRoleEntity);
    }

    @Override
    public void saveNewRolePrivilege(RolePrivilege rolePrivilege) {
        RoleEntity roleEntity = RoleEntity.domainToEntity(rolePrivilege.getRole());
        PrivilegeEntity privilegeEntity = PrivilegeEntity.domainToEntity(rolePrivilege.getPrivilege());
        RolePrivilegeEntity rolePrivilegeEntity = new RolePrivilegeEntity(roleEntity, privilegeEntity);
        rolePrivilegeRepository.save(rolePrivilegeEntity);
    }

    @Override
    public Optional<Role> findRoleByRoleName(RoleNames roleName) {
        Optional<RoleEntity> optionalRoleEntity = roleRepository.findByRoleName(roleName);

        if (optionalRoleEntity.isPresent()) {
            Role role = Role.entityToDomain(optionalRoleEntity.get());

            return Optional.of(role);
        }

        return Optional.empty();
    }

    @Override
    public Optional<RolePrivilege> findRolePrivilegeByRoleAndPrivilege(Role role, Privilege privilege) {
        RoleEntity roleEntity = RoleEntity.domainToEntity(role);
        PrivilegeEntity privilegeEntity = PrivilegeEntity.domainToEntity(privilege);
        Optional<RolePrivilegeEntity> optionalRolePrivilegeEntity = rolePrivilegeRepository.findByRoleAndPrivilege(roleEntity, privilegeEntity);

        if (optionalRolePrivilegeEntity.isPresent()) {
            RolePrivilege rolePrivilege = RolePrivilege.entityToDomain(optionalRolePrivilegeEntity.get());
            return Optional.of(rolePrivilege);
        }

        return Optional.empty();
    }

    @Override
    public List<UserRole> findUserRoleByUser(User user) {
        UserEntity userEntity = UserEntity.domainToEntity(user);

        List<UserRoleEntity> userRoleEntityList = userRoleRepository.findByUser(userEntity);

        return userRoleEntityList.stream().map(UserRole::entityToDomain).toList();
    }
}
