package com.onetwo.userservice.entity.role;

import com.onetwo.userservice.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class RolePrivilege extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "id")
    private Privilege privilege;

    public RolePrivilege(Role role, Privilege privilege) {
        this.role = role;
        this.privilege = privilege;
    }
}
