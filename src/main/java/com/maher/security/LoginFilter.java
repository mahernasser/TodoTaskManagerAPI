package com.maher.security;

import com.maher.services.UserService;
import com.mongodb.lang.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


@Component
public class LoginFilter extends OncePerRequestFilter {

    @Autowired
    private TokenHandler tokenHandler;

    @Autowired
    private UserService userService;


    @Value("${auth.header}")
    private String HEADER_TOKEN;


    @Override
    protected void doFilterInternal(@Nullable HttpServletRequest req, @Nullable HttpServletResponse res, @Nullable FilterChain filterChain)
            throws ServletException, IOException {
        // get the token from the header => validate token

        final String header = req.getHeader(HEADER_TOKEN);
        if (header == null) {
            filterChain.doFilter(req, res);
            return;
        }

        final String headerValue = Optional.ofNullable(header).orElse("");
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        if (!headerValue.isEmpty() && securityContext.getAuthentication() == null) {
            String token = headerValue.substring("Bearer ".length());
            String username = tokenHandler.getUseNameFromToken(token);
            if (username != null) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                if (tokenHandler.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(req, res);
    }
}
