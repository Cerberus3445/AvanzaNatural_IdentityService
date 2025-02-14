package com.cerberus.avanzanaturaldentityservice.service;


import com.cerberus.avanzanaturaldentityservice.model.UserCredential;

public interface AuthService {

    String saveUser(UserCredential credential);

    String generateToken(String email);

    void validateToken(String token);
}
