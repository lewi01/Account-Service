package com.lewisCode.accountservice.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class myUserDetailService implements UserDetails {

    private final String email;
    private final String password;
    private final List<GrantedAuthority> authorities;
    private final boolean accountNonLocked;

    public myUserDetailService(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authorities = Arrays.stream(user.getEmail().split(","))
                      .map(SimpleGrantedAuthority::new)
                     .collect(Collectors.toList());
        this.accountNonLocked = user.isAccountNonLocked();
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
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
