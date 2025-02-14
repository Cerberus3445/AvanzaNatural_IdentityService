package com.cerberus.avanzanaturaldentityservice.service;

public interface JwtService {

    void validateToken(String token);

    String generateToken(String email);

}
