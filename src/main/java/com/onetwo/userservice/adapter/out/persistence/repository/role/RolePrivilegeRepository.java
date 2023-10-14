package com.onetwo.userservice.adapter.out.persistence.repository.role;

import com.onetwo.userservice.adapter.out.persistence.entity.role.Privilege;
import com.onetwo.userservice.adapter.out.persistence.entity.role.Role;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RolePrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolePrivilegeRepository extends JpaRepository<RolePrivilege, Long> {
    Optional<RolePrivilege> findByRoleAndPrivilege(Role role, Privilege privilege);

    List<RolePrivilege> findByRole(Role role);
}
