package com.cerberus.avanzanaturaldentityservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AvanzaNaturalIdentityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AvanzaNaturalIdentityServiceApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
