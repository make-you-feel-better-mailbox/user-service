package com.onetwo.userservice.application.port.out.role;

import com.onetwo.userservice.domain.role.Role;
import com.onetwo.userservice.domain.role.UserRole;
import com.onetwo.userservice.domain.user.User;

import java.util.Optional;

public interface ReadUserRolePort {
    Optional<UserRole> findByUserAndRole(User user, Role role);
}
