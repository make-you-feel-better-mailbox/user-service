package com.onetwo.userservice.application.port.out.role;

import com.onetwo.userservice.adapter.out.persistence.entity.role.Privilege;
import com.onetwo.userservice.adapter.out.persistence.entity.role.PrivilegeNames;
import com.onetwo.userservice.adapter.out.persistence.entity.role.Role;

import java.util.List;

public interface CreatePrivilegePort {

    Privilege createPrivilegeIfNotFound(PrivilegeNames privilegeName);

    void mappingRoleAndPrivilege(Role role, List<Privilege> privileges);
}
