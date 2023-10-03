package com.onetwo.userservice.entity.role;

import java.time.Instant;

import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleNames roleName;

    public Role(RoleNames roleName) {
        this.roleName = roleName;
        setCreatedAt(Instant.now());
        setCreateUser(GlobalStatus.SYSTEM);
    }
}
