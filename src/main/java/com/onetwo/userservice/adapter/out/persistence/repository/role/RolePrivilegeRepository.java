package com.onetwo.userservice.adapter.out.persistence.repository.role;

import com.onetwo.userservice.adapter.out.persistence.entity.role.PrivilegeEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RoleEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RolePrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolePrivilegeRepository extends JpaRepository<RolePrivilegeEntity, Long> {
    Optional<RolePrivilegeEntity> findByRoleAndPrivilege(RoleEntity role, PrivilegeEntity privilege);

    List<RolePrivilegeEntity> findByRole(RoleEntity role);
}
