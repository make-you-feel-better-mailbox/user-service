package com.onetwo.userservice.application.port.in.role.usecase;

import com.onetwo.userservice.adapter.out.persistence.entity.role.RoleEntity;
import com.onetwo.userservice.domain.role.RoleNames;

public interface CreateRoleUseCase {
    RoleEntity createRoleIfNotFound(RoleNames roleAdmin);
}
