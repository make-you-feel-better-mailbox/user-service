package com.onetwo.userservice.repository.role;

import com.onetwo.userservice.entity.role.Privilege;
import com.onetwo.userservice.entity.role.PrivilegeNames;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Optional<Privilege> findByPrivilegeName(PrivilegeNames privilegeName);
}
