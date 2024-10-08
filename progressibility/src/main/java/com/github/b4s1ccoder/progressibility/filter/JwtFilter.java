package com.github.b4s1ccoder.progressibility.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.b4s1ccoder.progressibility.security.UserEntityIncludedAuthToken;
import com.github.b4s1ccoder.progressibility.security.UserEntityIncludedUserDetails;
import com.github.b4s1ccoder.progressibility.utils.JWTUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest req, HttpServletResponse res, FilterChain chain
    ) throws ServletException, IOException {

        String authHeader = req.getHeader("Authorization");
        String email = null;
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = jwtUtils.extractId(token);
        }

        if (email != null && jwtUtils.isTokenValid(token)) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (userDetails instanceof UserEntityIncludedUserDetails userEntityIncludedUserDetails) {
                
                UserEntityIncludedAuthToken auth = new UserEntityIncludedAuthToken(
                    userEntityIncludedUserDetails.getUser(), null, userDetails.getAuthorities()
                );

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            // if (jwtUtils.isTokenValid(token)) {

            //     UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            //         userDetails, null, userDetails.getAuthorities()
            //     );

            //     auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            //     SecurityContextHolder.getContext().setAuthentication(auth);
            // }
        }

        chain.doFilter(req, res);
    }
}
