package com.github.b4s1ccoder.progressibility.security;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.github.b4s1ccoder.progressibility.entity.User;

@Component
public class SecurityUtils {
    public Optional<User> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof UserEntityIncludedAuthToken) {
            UserEntityIncludedAuthToken uiat = (UserEntityIncludedAuthToken) authentication;
            return Optional.of(uiat.getUser());
        }

        return Optional.empty();
    }
}
