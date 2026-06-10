package com.pelaporan.pelaporanjalan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pelaporan.pelaporanjalan.model.User;
import com.pelaporan.pelaporanjalan.repository.UserRepository;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        System.out.println("=================================");
        System.out.println("LOGIN EMAIL : " + email);
        System.out.println("=================================");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("USER TIDAK DITEMUKAN");
                    return new UsernameNotFoundException("User tidak ditemukan");
                });

        System.out.println("USER DITEMUKAN : " + user.getEmail());
        System.out.println("PASSWORD DB : " + user.getPassword());
        System.out.println("ROLE : " + user.getRole());

        return org.springframework.security.core.userdetails.User
        .withUsername(user.getEmail())
        .password(user.getPassword())
        .roles(user.getRole().replace("ROLE_", ""))
        .build();
    }
}