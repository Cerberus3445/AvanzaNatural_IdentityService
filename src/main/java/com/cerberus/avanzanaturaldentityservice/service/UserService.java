package com.cerberus.avanzanaturaldentityservice.service;

import com.cerberus.avanzanaturaldentityservice.dto.UserDto;
import com.cerberus.avanzanaturaldentityservice.model.UserCredential;

public interface UserService {

    void save(UserDto userDto);

    UserCredential findByEmail(String email);
}
