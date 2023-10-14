package com.onetwo.userservice.application.port.out.role;

import com.onetwo.userservice.adapter.out.persistence.entity.role.Privilege;
import com.onetwo.userservice.adapter.out.persistence.entity.role.Role;

import java.util.List;

public interface ReadPrivilegePort {

    List<Privilege> getPrivilegeByRole(Role role);
}
