package com.onetwo.userservice.entity.role;

import java.time.Instant;

import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class RolePrivilege extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "privilege_id")
    private Privilege privilege;

    public RolePrivilege(Role role, Privilege privilege) {
        this.role = role;
        this.privilege = privilege;
        setCreatedAt(Instant.now());
        setCreateUser(GlobalStatus.SYSTEM);
    }
}
