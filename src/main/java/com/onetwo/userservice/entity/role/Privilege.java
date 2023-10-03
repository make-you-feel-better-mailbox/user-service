package com.onetwo.userservice.entity.role;

import java.time.Instant;

import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Privilege extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrivilegeNames privilegeName;

    public Privilege(PrivilegeNames privilegeName) {
        this.privilegeName = privilegeName;
        setCreatedAt(Instant.now());
        setCreateUser(GlobalStatus.SYSTEM);
    }
}
