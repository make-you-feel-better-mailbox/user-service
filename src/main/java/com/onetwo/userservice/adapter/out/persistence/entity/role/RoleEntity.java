package com.onetwo.userservice.adapter.out.persistence.entity.role;

import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.adapter.out.persistence.entity.BaseEntity;
import com.onetwo.userservice.domain.role.RoleNames;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
public class RoleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleNames roleName;

    public RoleEntity(RoleNames roleName) {
        this.roleName = roleName;
        setCreatedAt(Instant.now());
        setCreateUser(GlobalStatus.SYSTEM);
    }
}
