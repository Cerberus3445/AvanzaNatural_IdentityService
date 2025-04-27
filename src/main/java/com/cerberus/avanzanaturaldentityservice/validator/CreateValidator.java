package com.cerberus.avanzanaturaldentityservice.validator;


import com.cerberus.avanzanaturaldentityservice.dto.UserDto;
import com.cerberus.avanzanaturaldentityservice.exception.AlreadyExistsException;
import com.cerberus.avanzanaturaldentityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateValidator {

    private final UserService userService;

    public void validate(UserDto userDto){
        if(this.userService.getByEmail(userDto.getEmail()).isPresent()){
            throw new AlreadyExistsException("User with this email already exists");
        }
    }

}
