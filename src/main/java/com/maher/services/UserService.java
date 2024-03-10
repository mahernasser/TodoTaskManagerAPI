package com.maher.services;

import com.maher.enitites.AppUser;
import com.maher.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepo.findAppUserByEmail(username);
        if (appUser == null) {
            System.out.println("User not found with email: " + username);
            throw new UsernameNotFoundException("User not found");
        } else {
            return appUser;
        }
    }

    public void save(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public List<AppUser> getAllUsers() {
        return userRepo.findAll();
    }

}
