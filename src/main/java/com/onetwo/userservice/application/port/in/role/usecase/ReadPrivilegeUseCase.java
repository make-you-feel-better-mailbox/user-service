package com.onetwo.userservice.application.port.in.role.usecase;

import com.onetwo.userservice.domain.role.Privilege;
import com.onetwo.userservice.domain.role.Role;

import java.util.List;

public interface ReadPrivilegeUseCase {
    /**
     * Get privilege by role use case,
     * Find privilege by role on persistence
     *
     * @param role role
     * @return List of privilege
     */
    List<Privilege> getPrivilegeByRole(Role role);
}
