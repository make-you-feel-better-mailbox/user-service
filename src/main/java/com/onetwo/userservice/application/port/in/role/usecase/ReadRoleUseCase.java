package com.onetwo.userservice.application.port.in.role.usecase;

import com.onetwo.userservice.domain.role.Role;
import com.onetwo.userservice.domain.user.User;

import java.util.List;

public interface ReadRoleUseCase {

    /**
     * Get roles by user use case,
     * Find user's roles on persistence
     *
     * @param user user domain
     * @return List of user's roles
     */
    List<Role> getRolesByUser(User user);
}
