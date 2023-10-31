package com.onetwo.userservice.domain.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MyUserDetail implements UserDetails {

    private String userId;
    private String password;
    private boolean state;
    private Collection<GrantedAuthority> authorities;

    public MyUserDetail(String userId, String password, boolean state, Collection<GrantedAuthority> authorities) {
        this.userId = userId;
        this.password = password;
        this.state = state;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !state;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !state;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !state;
    }

    @Override
    public boolean isEnabled() {
        return !state;
    }
}
