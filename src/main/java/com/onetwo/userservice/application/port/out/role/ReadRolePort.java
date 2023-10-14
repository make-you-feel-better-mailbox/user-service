package com.onetwo.userservice.application.port.out.role;

import com.onetwo.userservice.adapter.out.persistence.entity.role.Role;
import com.onetwo.userservice.adapter.out.persistence.entity.user.User;

import java.util.List;

public interface ReadRolePort {

    List<Role> getRolesByUser(User user);
}
