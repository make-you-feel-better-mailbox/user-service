package com.onetwo.userservice.application.port.out.role;

import com.onetwo.userservice.adapter.out.persistence.entity.role.PrivilegeEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RoleEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RolePrivilegeEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.UserRole;
import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;
import com.onetwo.userservice.domain.role.RoleNames;

import java.util.List;
import java.util.Optional;

public interface ReadRolePort {
    Optional<RoleEntity> findRoleByRoleName(RoleNames roleName);

    Optional<RolePrivilegeEntity> findRolePrivilegeByRoleAndPrivilege(RoleEntity adminRole, PrivilegeEntity privilege);

    List<UserRole> findUserRoleByUser(UserEntity user);
}
