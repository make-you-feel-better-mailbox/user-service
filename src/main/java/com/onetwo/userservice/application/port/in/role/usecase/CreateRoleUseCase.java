package com.onetwo.userservice.application.port.in.role.usecase;

import com.onetwo.userservice.domain.role.Role;
import com.onetwo.userservice.domain.role.RoleNames;

public interface CreateRoleUseCase {
    Role createRoleIfNotFound(RoleNames roleAdmin);
}
