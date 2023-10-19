package com.onetwo.userservice.domain.role;

import com.onetwo.userservice.adapter.out.persistence.entity.role.RoleEntity;
import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.domain.BaseDomain;
import lombok.Getter;

import java.time.Instant;

@Getter
public class Role extends BaseDomain {

    private Long id;

    private RoleNames roleName;

    private Role(Long id, RoleNames roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public static Role createRoleByRoleName(RoleNames roleName) {
        Role role = new Role(null, roleName);
        role.setCreatedAt(Instant.now());
        role.setCreateUser(GlobalStatus.SYSTEM);
        return role;
    }

    public static Role entityToDomain(RoleEntity roleEntity) {
        Role role = new Role(roleEntity.getId(), roleEntity.getRoleName());
        role.setMetaDataByEntity(roleEntity);
        return role;
    }
}
