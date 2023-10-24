package com.onetwo.userservice.adapter.out.persistence.entity.role;

import com.onetwo.userservice.adapter.out.persistence.entity.BaseEntity;
import com.onetwo.userservice.domain.role.Role;
import com.onetwo.userservice.domain.role.RoleNames;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class RoleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleNames roleName;

    private RoleEntity(Long id, RoleNames roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public static RoleEntity domainToEntity(Role role) {
        RoleEntity roleEntity = new RoleEntity(role.getId(), role.getRoleName());
        roleEntity.setMetaDataByDomain(role);
        return roleEntity;
    }
}
