package com.lewisCode.accountservice.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class myUserDetailService implements UserDetails {

    private final String email;
    private final String password;
   // private final List<GrantedAuthority> authorities;

    public myUserDetailService(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
//        this.authorities = Arrays.stream(user.getEmail().split(","))
//                      .map(SimpleGrantedAuthority::new)
//                      .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
        return true;
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
