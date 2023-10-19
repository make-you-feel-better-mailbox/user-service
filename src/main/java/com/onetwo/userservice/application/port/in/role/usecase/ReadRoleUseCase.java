package com.onetwo.userservice.application.port.in.role.usecase;

import com.onetwo.userservice.domain.role.Role;
import com.onetwo.userservice.domain.user.User;

import java.util.List;

public interface ReadRoleUseCase {
    List<Role> getRolesByUser(User user);
}
