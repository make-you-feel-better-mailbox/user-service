package com.onetwo.userservice.application.port.out.role;

import com.onetwo.userservice.adapter.out.persistence.entity.role.PrivilegeEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RoleEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RolePrivilegeEntity;
import com.onetwo.userservice.domain.role.PrivilegeNames;

import java.util.List;
import java.util.Optional;

public interface ReadPrivilegePort {
    Optional<PrivilegeEntity> findPrivilegeByPrivilegeName(PrivilegeNames privilegeName);

    List<RolePrivilegeEntity> findRolePrivilegeByRole(RoleEntity role);
}
