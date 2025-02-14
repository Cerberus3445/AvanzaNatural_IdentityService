package com.cerberus.avanzanaturaldentityservice.service;


import com.cerberus.avanzanaturaldentityservice.model.UserCredential;

public interface UserService {

    UserCredential findByEmail(String email);
}
