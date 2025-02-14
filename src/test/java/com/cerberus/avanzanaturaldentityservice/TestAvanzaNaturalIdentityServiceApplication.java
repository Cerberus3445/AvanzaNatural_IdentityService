package com.cerberus.avanzanaturaldentityservice;

import org.springframework.boot.SpringApplication;

public class TestAvanzaNaturalIdentityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(AvanzaNaturalIdentityServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
