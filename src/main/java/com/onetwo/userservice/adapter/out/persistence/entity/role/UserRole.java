package com.onetwo.userservice.adapter.out.persistence.entity.role;

import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;
import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.adapter.out.persistence.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
public class UserRole extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    public UserRole(UserEntity user, RoleEntity role) {
        this.user = user;
        this.role = role;
        setCreateUser(GlobalStatus.SYSTEM);
        setCreatedAt(Instant.now());
    }
}
