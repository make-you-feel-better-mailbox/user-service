package com.onetwo.userservice.domain.role;

import com.onetwo.userservice.adapter.out.persistence.entity.role.UserRoleEntity;
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

    private UserRole(Long id, User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public static UserRole createUserRole(User user, Role role) {
        UserRole userRole = new UserRole(null, user, role);
        userRole.setCreateUser(GlobalStatus.SYSTEM);
        userRole.setCreatedAt(Instant.now());
        return userRole;
    }

    public static UserRole entityToDomain(UserRoleEntity userRoleEntity) {
        User user = User.entityToDomain(userRoleEntity.getUser());
        Role role = Role.entityToDomain(userRoleEntity.getRole());
        UserRole userRole = new UserRole(userRoleEntity.getId(), user, role);
        userRole.setMetaDataByEntity(userRoleEntity);
        return userRole;
    }
}
