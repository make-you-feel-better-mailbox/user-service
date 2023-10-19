package com.onetwo.userservice.application.port.out.role;

import com.onetwo.userservice.domain.role.Role;
import com.onetwo.userservice.domain.role.RolePrivilege;

public interface CreateRolePort {
    Role saveNewRole(Role role);

    void saveNewRolePrivilege(RolePrivilege rolePrivilege);
}
