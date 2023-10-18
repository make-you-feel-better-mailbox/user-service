package com.onetwo.userservice.domain.role;

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
        setCreatedAt(Instant.now());
        setCreateUser(GlobalStatus.SYSTEM);
    }
}
