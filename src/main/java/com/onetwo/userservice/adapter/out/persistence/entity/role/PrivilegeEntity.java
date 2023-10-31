package com.onetwo.userservice.adapter.out.persistence.entity.role;

import com.onetwo.userservice.adapter.out.persistence.entity.BaseEntity;
import com.onetwo.userservice.domain.role.Privilege;
import com.onetwo.userservice.domain.role.PrivilegeNames;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PrivilegeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrivilegeNames privilegeName;

    private PrivilegeEntity(Long id, PrivilegeNames privilegeName) {
        this.id = id;
        this.privilegeName = privilegeName;
    }

    public static PrivilegeEntity domainToEntity(Privilege privilege) {
        PrivilegeEntity privilegeEntity = new PrivilegeEntity(privilege.getId(), privilege.getPrivilegeName());
        privilegeEntity.setMetaDataByDomain(privilege);
        return privilegeEntity;
    }
}
