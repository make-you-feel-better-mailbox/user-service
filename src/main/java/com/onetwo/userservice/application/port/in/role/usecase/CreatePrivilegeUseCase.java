package com.onetwo.userservice.application.port.in.role.usecase;

import com.onetwo.userservice.domain.role.Privilege;
import com.onetwo.userservice.domain.role.PrivilegeNames;

public interface CreatePrivilegeUseCase {
    /**
     * Create privilege if not found use case,
     * Save privilege if not found on persistence
     *
     * @param privilegeName privilegeName
     * @return saved privilege
     */
    Privilege createPrivilegeIfNotFound(PrivilegeNames privilegeName);
}
