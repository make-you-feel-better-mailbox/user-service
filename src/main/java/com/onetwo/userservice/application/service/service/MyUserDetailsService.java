package com.onetwo.userservice.application.service.service;

import com.onetwo.userservice.adapter.out.persistence.entity.role.PrivilegeEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RoleEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;
import com.onetwo.userservice.application.port.in.role.usecase.ReadPrivilegeUseCase;
import com.onetwo.userservice.application.port.in.role.usecase.ReadRoleUseCase;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.common.exceptions.BadRequestException;
import com.onetwo.userservice.common.exceptions.NotFoundResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final ReadUserPort readUserPort;

    private final ReadRoleUseCase readRoleUseCase;
    private final ReadPrivilegeUseCase readPrivilegeUseCase;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = readUserPort.findByUserId(username).orElseThrow(() -> new NotFoundResourceException("user-id does not exist"));

        return createUserDetails(user);
    }

    private UserDetails createUserDetails(UserEntity user) {
        if (user.isUserWithdraw())
            throw new BadRequestException(user.getUserId() + " -> 탈퇴처리된 회원입니다.");

        Set<GrantedAuthority> authorities = getGrantedAuthoritiesByUser(user);

        return new User(user.getUserId(),
                user.getPassword(),
                authorities);
    }

    public Set<GrantedAuthority> getGrantedAuthoritiesByUser(UserEntity user) {
        List<RoleEntity> roles = readRoleUseCase.getRolesByUser(user);

        Set<GrantedAuthority> authorities = new HashSet<>();

        for (RoleEntity role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName().getValue()));
            for (PrivilegeEntity privilege : readPrivilegeUseCase.getPrivilegeByRole(role)) {
                authorities.add(new SimpleGrantedAuthority(privilege.getPrivilegeName().getValue()));
            }
        }
        return authorities;
    }
}

