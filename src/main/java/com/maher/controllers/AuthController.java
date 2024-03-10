package com.maher.controllers;

import com.maher.enitites.JwtResponse;
import com.maher.enitites.LoginRequest;
import com.maher.security.TokenHandler;
import com.maher.services.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private TokenHandler tokenHandler;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = {"", "/"})
    public JwtResponse login(@RequestBody LoginRequest loginRequest) {
        System.out.println("loginRequest = " + loginRequest);
        try {
            final Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
            final String token = tokenHandler.tokenGenerator(userDetails);
            return new JwtResponse(token);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found", e);
        } catch (Exception e) {
            System.out.println("Error during authentication: " + e.getMessage());
            throw e;
        }
    }
}
