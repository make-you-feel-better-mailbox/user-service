package com.onetwo.userservice.domain.role;

import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.domain.BaseDomain;
import com.onetwo.userservice.domain.user.User;
import lombok.Getter;

import java.time.Instant;

@Getter
public class UserRole extends BaseDomain {

    private Long id;

    private User user;

    private Role role;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
        setCreateUser(GlobalStatus.SYSTEM);
        setCreatedAt(Instant.now());
    }
}
