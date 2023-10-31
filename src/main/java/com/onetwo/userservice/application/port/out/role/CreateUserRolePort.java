package com.onetwo.userservice.application.port.out.role;

import com.onetwo.userservice.domain.role.UserRole;

public interface CreateUserRolePort {
    void saveNewUserRole(UserRole userRole);
}
