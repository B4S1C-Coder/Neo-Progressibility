package com.github.b4s1ccoder.progressibility.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.github.b4s1ccoder.progressibility.entity.User;

public class UserEntityIncludedUserDetails implements UserDetails {
    private final User user;

    public UserEntityIncludedUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(
            role -> (GrantedAuthority) () -> "ROLE_" + role
        ).toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public User getUser() {
        return user;
    }
}
