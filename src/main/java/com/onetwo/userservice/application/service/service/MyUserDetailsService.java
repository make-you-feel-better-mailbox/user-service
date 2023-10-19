package com.onetwo.userservice.application.service.service;

import com.onetwo.userservice.application.port.in.role.usecase.ReadPrivilegeUseCase;
import com.onetwo.userservice.application.port.in.role.usecase.ReadRoleUseCase;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.common.exceptions.BadRequestException;
import com.onetwo.userservice.common.exceptions.NotFoundResourceException;
import com.onetwo.userservice.domain.role.Privilege;
import com.onetwo.userservice.domain.role.Role;
import com.onetwo.userservice.domain.user.MyUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        com.onetwo.userservice.domain.user.User user = readUserPort.findByUserId(username).orElseThrow(() -> new NotFoundResourceException("user-id does not exist"));

        return createUserDetails(user);
    }

    private UserDetails createUserDetails(com.onetwo.userservice.domain.user.User user) {
        if (Boolean.TRUE.equals(user.getState()))
            throw new BadRequestException(user.getUserId() + " -> 탈퇴처리된 회원입니다.");

        Set<GrantedAuthority> authorities = getGrantedAuthoritiesByUser(user);

        return new MyUserDetail(user.getUserId(),
                user.getPassword(),
                user.getState(),
                authorities);
    }

    public Set<GrantedAuthority> getGrantedAuthoritiesByUser(com.onetwo.userservice.domain.user.User user) {
        List<Role> roles = readRoleUseCase.getRolesByUser(user);

        Set<GrantedAuthority> authorities = new HashSet<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName().getValue()));
            for (Privilege privilege : readPrivilegeUseCase.getPrivilegeByRole(role)) {
                authorities.add(new SimpleGrantedAuthority(privilege.getPrivilegeName().getValue()));
            }
        }
        return authorities;
    }
}

