package com.cerberus.avanzanaturaldentityservice.service;

import com.cerberus.avanzanaturaldentityservice.dto.UserDto;
import com.cerberus.avanzanaturaldentityservice.model.UserCredential;

public interface JwtService {

    void validateToken(String token);

    String generateToken(UserCredential email);

}
