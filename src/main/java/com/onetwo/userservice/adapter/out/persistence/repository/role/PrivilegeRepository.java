package com.onetwo.userservice.adapter.out.persistence.repository.role;

import com.onetwo.userservice.adapter.out.persistence.entity.role.PrivilegeEntity;
import com.onetwo.userservice.domain.role.PrivilegeNames;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, Long> {
    Optional<PrivilegeEntity> findByPrivilegeName(PrivilegeNames privilegeName);
}
