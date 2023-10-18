package com.onetwo.userservice.adapter.out.persistence.repository.role;

import com.onetwo.userservice.adapter.out.persistence.entity.role.RoleEntity;
import com.onetwo.userservice.domain.role.RoleNames;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRoleName(RoleNames roleName);
}
