package com.onetwo.userservice.domain.role;

import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.domain.BaseDomain;
import lombok.Getter;

import java.time.Instant;

@Getter
public class Privilege extends BaseDomain {

    private Long id;

    private PrivilegeNames privilegeName;

    public Privilege(PrivilegeNames privilegeName) {
        this.privilegeName = privilegeName;
        setCreatedAt(Instant.now());
        setCreateUser(GlobalStatus.SYSTEM);
    }
}
