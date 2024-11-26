package com.onetwo.userservice.application.port.in.role.usecase;

import com.onetwo.userservice.domain.role.Privilege;
import com.onetwo.userservice.domain.role.Role;

import java.util.List;

public interface MappingRoleUseCase {

    /**
     * Mapping role and privilege use case,
     * on persistence 1:M (one-to-many) mapping
     *
     * @param role       role
     * @param privileges List of privilege
     */
    void mappingRoleAndPrivilege(Role role, List<Privilege> privileges);
}
