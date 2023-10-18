package com.onetwo.userservice.application.port.in.role.usecase;

import com.onetwo.userservice.adapter.out.persistence.entity.role.PrivilegeEntity;
import com.onetwo.userservice.domain.role.PrivilegeNames;

public interface CreatePrivilegeUseCase {
    PrivilegeEntity createPrivilegeIfNotFound(PrivilegeNames readPrivilege);
}
