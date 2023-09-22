package com.onetwo.userservice.service.service;

import com.onetwo.userservice.entity.role.Privilege;
import com.onetwo.userservice.entity.role.PrivilegeNames;
import com.onetwo.userservice.entity.role.Role;

import java.util.List;

public interface PrivilegeService {
    Privilege createPrivilegeIfNotFound(PrivilegeNames privilegeName);

    void mappingRoleAndPrivilege(Role role, List<Privilege> privileges);

    List<Privilege> getPrivilegeByRole(Role role);
}
