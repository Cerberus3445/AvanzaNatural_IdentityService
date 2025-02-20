package com.cerberus.avanzanaturaldentityservice.service;


import com.cerberus.avanzanaturaldentityservice.dto.UserDto;
import com.cerberus.avanzanaturaldentityservice.model.UserCredential;

public interface AuthService {

    String saveUser(UserDto userDto);

    String generateToken(String email);

    void validateToken(String token);
}
