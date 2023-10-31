package com.onetwo.userservice.adapter.out.persistence.entity.role;

import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.adapter.out.persistence.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
public class RolePrivilegeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "privilege_id")
    private PrivilegeEntity privilege;

    public RolePrivilegeEntity(RoleEntity role, PrivilegeEntity privilege) {
        this.role = role;
        this.privilege = privilege;
        setCreatedAt(Instant.now());
        setCreateUser(GlobalStatus.SYSTEM);
    }
}
