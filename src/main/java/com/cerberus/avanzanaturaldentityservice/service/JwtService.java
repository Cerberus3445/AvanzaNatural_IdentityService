package com.cerberus.avanzanaturaldentityservice.service;

import com.cerberus.avanzanaturaldentityservice.model.UserCredential;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface JwtService {

    String extractEmail(String token);

    String generateToken(UserCredential email);

    Collection<? extends GrantedAuthority> extractRole(String token);

}
