package com.onetwo.userservice.application.port.out.role;

import com.onetwo.userservice.domain.role.*;
import com.onetwo.userservice.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface ReadRolePort {
    Optional<Role> findRoleByRoleName(RoleNames roleName);

    Optional<RolePrivilege> findRolePrivilegeByRoleAndPrivilege(Role role, Privilege privilege);

    List<UserRole> findUserRoleByUser(User user);
}
