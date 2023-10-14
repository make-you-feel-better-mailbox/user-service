package com.onetwo.userservice.application.port.out.role;

import com.onetwo.userservice.adapter.out.persistence.entity.role.Role;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RoleNames;
import com.onetwo.userservice.adapter.out.persistence.entity.user.User;

public interface CreateRolePort {

    void createNewUserRole(User savedUser);

    Role createRoleIfNotFound(RoleNames roleAdmin);
}
