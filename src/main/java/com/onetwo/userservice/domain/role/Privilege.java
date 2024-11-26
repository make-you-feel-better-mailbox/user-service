package com.onetwo.userservice.domain.role;

import com.onetwo.userservice.adapter.out.persistence.entity.role.PrivilegeEntity;
import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.domain.BaseDomain;
import lombok.Getter;

import java.time.Instant;

@Getter
public class Privilege extends BaseDomain {

    private Long id;

    private PrivilegeNames privilegeName;

    private Privilege(Long id, PrivilegeNames privilegeName) {
        this.id = id;
        this.privilegeName = privilegeName;
    }

    public static Privilege createPrivilege(PrivilegeNames privilegeName) {
        Privilege privilege = new Privilege(null, privilegeName);
        privilege.setCreatedAt(Instant.now());
        privilege.setCreateUser(GlobalStatus.SYSTEM);
        return privilege;
    }

    public static Privilege entityToDomain(PrivilegeEntity privilegeEntity) {
        Privilege privilege = new Privilege(privilegeEntity.getId(), privilegeEntity.getPrivilegeName());
        privilege.setMetaDataByEntity(privilegeEntity);
        return privilege;
    }
}
