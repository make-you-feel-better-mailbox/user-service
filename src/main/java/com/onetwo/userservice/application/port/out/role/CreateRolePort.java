package com.onetwo.userservice.application.port.out.role;

import com.onetwo.userservice.adapter.out.persistence.entity.role.RoleEntity;

public interface CreateRolePort {
    void saveNewRole(RoleEntity role);
}
