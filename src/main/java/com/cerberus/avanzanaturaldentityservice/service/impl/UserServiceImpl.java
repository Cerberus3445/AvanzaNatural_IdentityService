package com.cerberus.avanzanaturaldentityservice.service.impl;

import com.cerberus.avanzanaturaldentityservice.dto.UserDto;
import com.cerberus.avanzanaturaldentityservice.exception.NotFoundException;
import com.cerberus.avanzanaturaldentityservice.mapper.EntityDtoMapper;
import com.cerberus.avanzanaturaldentityservice.model.UserCredential;
import com.cerberus.avanzanaturaldentityservice.repository.UserCredentialRepository;
import com.cerberus.avanzanaturaldentityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserCredentialRepository userCredentialRepository;

    private final EntityDtoMapper mapper;

    @Override
    public void save(UserDto userDto) {
        this.userCredentialRepository.save(this.mapper.toEntity(userDto));
    }

    @Override
    @Transactional(readOnly = true)
    public UserCredential findByEmail(String email) {
        return this.userCredentialRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found."));
    }
}
