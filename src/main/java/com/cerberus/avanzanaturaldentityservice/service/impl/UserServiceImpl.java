package com.cerberus.avanzanaturaldentityservice.service.impl;

import com.cerberus.avanzanaturaldentityservice.model.UserCredential;
import com.cerberus.avanzanaturaldentityservice.repository.UserCredentialRepository;
import com.cerberus.avanzanaturaldentityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserCredentialRepository userCredentialRepository;

    @Override
    public UserCredential findByEmail(String email) {
        return this.userCredentialRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
