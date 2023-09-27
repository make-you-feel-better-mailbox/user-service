package com.onetwo.userservice.service.service;

import com.onetwo.userservice.common.exceptions.BadRequestException;
import com.onetwo.userservice.entity.role.Privilege;
import com.onetwo.userservice.entity.role.Role;
import com.onetwo.userservice.repository.user.UserRepository;
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

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PrivilegeService privilegeService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.onetwo.userservice.entity.user.User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("userId : " + username + " was not found"));

        return createUserDetails(user);
    }

    private UserDetails createUserDetails(com.onetwo.userservice.entity.user.User user) {
        if (user.isUserWithdraw())
            throw new BadRequestException(user.getUserId() + " -> 탈퇴처리된 회원입니다.");

        Set<GrantedAuthority> authorities = getGrantedAuthoritiesByUser(user);

        return new User(user.getUserId(),
                user.getPassword(),
                authorities);
    }

    public Set<GrantedAuthority> getGrantedAuthoritiesByUser(com.onetwo.userservice.entity.user.User user) {
        List<Role> roles = roleService.getRolesByUser(user);

        Set<GrantedAuthority> authorities = new HashSet<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName().getValue()));
            for (Privilege privilege : privilegeService.getPrivilegeByRole(role)) {
                authorities.add(new SimpleGrantedAuthority(privilege.getPrivilegeName().getValue()));
            }
        }
        return authorities;
    }
}

