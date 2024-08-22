package com.github.b4s1ccoder.progressibility.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.github.b4s1ccoder.progressibility.entity.User;

public class UserEntityIncludedAuthToken extends UsernamePasswordAuthenticationToken {
    
    private final User user;

    public UserEntityIncludedAuthToken(User user, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), credentials, authorities);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
