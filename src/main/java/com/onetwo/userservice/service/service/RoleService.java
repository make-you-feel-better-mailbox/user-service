package com.onetwo.userservice.service.service;

import com.onetwo.userservice.entity.role.Role;
import com.onetwo.userservice.entity.role.RoleNames;
import com.onetwo.userservice.entity.user.User;

import java.util.List;

public interface RoleService {
    void createNewUserRole(User savedUser);

    Role createRoleIfNotFound(RoleNames roleAdmin);

    List<Role> getRolesByUser(User user);
}
