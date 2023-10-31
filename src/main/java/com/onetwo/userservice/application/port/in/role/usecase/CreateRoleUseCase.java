package com.onetwo.userservice.application.port.in.role.usecase;

import com.onetwo.userservice.domain.role.Role;
import com.onetwo.userservice.domain.role.RoleNames;

public interface CreateRoleUseCase {
    /**
     * Create role if not found use case,
     * Save role if not found on persistence
     *
     * @param roleName roleName
     * @return saved role
     */
    Role createRoleIfNotFound(RoleNames roleName);
}
