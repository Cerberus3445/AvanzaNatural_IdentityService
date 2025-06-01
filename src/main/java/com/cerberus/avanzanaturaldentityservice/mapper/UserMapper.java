package com.cerberus.avanzanaturaldentityservice.mapper;

import com.cerberus.avanzanaturaldentityservice.dto.UserDto;
import com.cerberus.avanzanaturaldentityservice.model.UserCredential;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mappable<UserCredential, UserDto>{

    private final ModelMapper modelMapper;

    @Override
    public UserCredential toEntity(UserDto userDto) {
        return this.modelMapper.map(userDto, UserCredential.class);
    }

    @Override
    public UserDto toDto(UserCredential userCredential) {
        return this.modelMapper.map(userCredential, UserDto.class);
    }
}
