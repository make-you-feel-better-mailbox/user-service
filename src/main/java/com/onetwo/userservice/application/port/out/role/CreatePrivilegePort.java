package com.onetwo.userservice.application.port.out.role;

import com.onetwo.userservice.adapter.out.persistence.entity.role.PrivilegeEntity;

public interface CreatePrivilegePort {
    void saveNewPrivilege(PrivilegeEntity privilege);
}
