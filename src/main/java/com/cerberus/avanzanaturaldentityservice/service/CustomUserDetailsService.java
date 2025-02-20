package com.cerberus.avanzanaturaldentityservice.service;

import com.cerberus.avanzanaturaldentityservice.exception.NotFoundException;
import com.cerberus.avanzanaturaldentityservice.model.UserCredential;
import com.cerberus.avanzanaturaldentityservice.repository.UserCredentialRepository;
import com.cerberus.avanzanaturaldentityservice.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserCredentialRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> credential = this.repository.findByEmail(username);
        return new CustomUserDetails(credential.orElseThrow(
                () -> new NotFoundException("User not found")
        ));
    }
}