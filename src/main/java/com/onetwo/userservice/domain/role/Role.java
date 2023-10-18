package com.onetwo.userservice.domain.role;

import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.domain.BaseDomain;
import lombok.Getter;

import java.time.Instant;

@Getter
public class Role extends BaseDomain {

    private Long id;

    private RoleNames roleName;

    public Role(RoleNames roleName) {
        this.roleName = roleName;
        setCreatedAt(Instant.now());
        setCreateUser(GlobalStatus.SYSTEM);
    }
}
