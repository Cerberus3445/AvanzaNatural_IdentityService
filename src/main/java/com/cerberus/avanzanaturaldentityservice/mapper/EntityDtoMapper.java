package com.cerberus.avanzanaturaldentityservice.mapper;

import com.cerberus.avanzanaturaldentityservice.dto.UserDto;
import com.cerberus.avanzanaturaldentityservice.model.UserCredential;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntityDtoMapper {

    UserDto toDto(UserCredential user);

    UserCredential toEntity(UserDto userDto);

}
