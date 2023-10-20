package com.onetwo.userservice.application.port.in.role.usecase;

import com.onetwo.userservice.domain.role.Privilege;
import com.onetwo.userservice.domain.role.PrivilegeNames;

public interface CreatePrivilegeUseCase {
    Privilege createPrivilegeIfNotFound(PrivilegeNames readPrivilege);
}
