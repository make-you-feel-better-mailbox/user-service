package com.onetwo.userservice.domain.role;

import com.onetwo.userservice.adapter.out.persistence.entity.role.RolePrivilegeEntity;
import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.domain.BaseDomain;
import lombok.Getter;

import java.time.Instant;

@Getter
public class RolePrivilege extends BaseDomain {

    private Long id;

    private Role role;

    private Privilege privilege;

    public RolePrivilege(Role role, Privilege privilege) {
        this.role = role;
        this.privilege = privilege;
    }

    public static RolePrivilege createRolePrivilege(Role role, Privilege privilege) {
        RolePrivilege rolePrivilege = new RolePrivilege(role, privilege);
        rolePrivilege.setCreatedAt(Instant.now());
        rolePrivilege.setCreateUser(GlobalStatus.SYSTEM);
        return rolePrivilege;
    }

    public static RolePrivilege entityToDomain(RolePrivilegeEntity rolePrivilegeEntity) {
        Role role = Role.entityToDomain(rolePrivilegeEntity.getRole());
        Privilege privilege = Privilege.entityToDomain(rolePrivilegeEntity.getPrivilege());
        RolePrivilege rolePrivilege = new RolePrivilege(role, privilege);
        rolePrivilege.setMetaDataByEntity(rolePrivilegeEntity);
        return rolePrivilege;
    }
}
