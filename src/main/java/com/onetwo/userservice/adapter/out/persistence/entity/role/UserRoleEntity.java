package com.onetwo.userservice.adapter.out.persistence.entity.role;

import com.onetwo.userservice.adapter.out.persistence.entity.BaseEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;
import com.onetwo.userservice.common.GlobalStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;

@Entity
@Getter
public class UserRoleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    private UserRoleEntity(Long id, UserEntity user, RoleEntity role) {
        this.id = id;
        this.user = user;
        this.role = role;
    }

    public static UserRoleEntity createUserRole(UserEntity user, RoleEntity role) {
        UserRoleEntity userRoleEntity = new UserRoleEntity(null, user, role);
        userRoleEntity.setCreatedAt(Instant.now());
        userRoleEntity.setCreateUser(GlobalStatus.SYSTEM);
        return userRoleEntity;
    }
}
