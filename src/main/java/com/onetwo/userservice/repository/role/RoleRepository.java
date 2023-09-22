package com.onetwo.userservice.repository.role;

import com.onetwo.userservice.entity.role.Role;
import com.onetwo.userservice.entity.role.RoleNames;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleNames roleName);
}
