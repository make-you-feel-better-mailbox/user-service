package com.onetwo.userservice.application.port.out.role;

import com.onetwo.userservice.domain.role.Privilege;
import com.onetwo.userservice.domain.role.PrivilegeNames;
import com.onetwo.userservice.domain.role.Role;
import com.onetwo.userservice.domain.role.RolePrivilege;

import java.util.List;
import java.util.Optional;

public interface ReadPrivilegePort {
    Optional<Privilege> findPrivilegeByPrivilegeName(PrivilegeNames privilegeName);

    List<RolePrivilege> findRolePrivilegeByRole(Role role);
}
