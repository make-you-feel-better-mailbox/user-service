package com.onetwo.userservice.application.service.service;

import com.onetwo.userservice.adapter.out.persistence.entity.role.Privilege;
import com.onetwo.userservice.adapter.out.persistence.entity.role.Role;
import com.onetwo.userservice.application.port.out.role.ReadPrivilegePort;
import com.onetwo.userservice.application.port.out.role.ReadRolePort;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.common.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final ReadUserPort readUserPort;

    private final ReadRolePort readRolePort;
    private final ReadPrivilegePort readPrivilegePort;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.onetwo.userservice.adapter.out.persistence.entity.user.User user = readUserPort.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("userId : " + username + " was not found"));

        return createUserDetails(user);
    }

    private UserDetails createUserDetails(com.onetwo.userservice.adapter.out.persistence.entity.user.User user) {
        if (user.isUserWithdraw())
            throw new BadRequestException(user.getUserId() + " -> 탈퇴처리된 회원입니다.");

        Set<GrantedAuthority> authorities = getGrantedAuthoritiesByUser(user);

        return new User(user.getUserId(),
                user.getPassword(),
                authorities);
    }

    public Set<GrantedAuthority> getGrantedAuthoritiesByUser(com.onetwo.userservice.adapter.out.persistence.entity.user.User user) {
        List<Role> roles = readRolePort.getRolesByUser(user);

        Set<GrantedAuthority> authorities = new HashSet<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName().getValue()));
            for (Privilege privilege : readPrivilegePort.getPrivilegeByRole(role)) {
                authorities.add(new SimpleGrantedAuthority(privilege.getPrivilegeName().getValue()));
            }
        }
        return authorities;
    }
}

