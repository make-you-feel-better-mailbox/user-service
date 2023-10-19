package com.onetwo.userservice.application.port.in.role.usecase;

import com.onetwo.userservice.domain.role.Privilege;
import com.onetwo.userservice.domain.role.Role;

import java.util.List;

public interface MappingRoleUseCase {
    void mappingRoleAndPrivilege(Role role, List<Privilege> privileges);
}
