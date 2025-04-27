package com.cerberus.avanzanaturaldentityservice.service;

import com.cerberus.avanzanaturaldentityservice.dto.UserDto;
import com.cerberus.avanzanaturaldentityservice.model.UserCredential;

import java.util.Optional;

public interface UserService {

    void save(UserDto userDto);

    Optional<UserCredential> getByEmail(String email);
}
