package com.onetwo.userservice.application.port.out.role;

import com.onetwo.userservice.domain.role.Privilege;

public interface CreatePrivilegePort {
    Privilege saveNewPrivilege(Privilege privilege);
}
