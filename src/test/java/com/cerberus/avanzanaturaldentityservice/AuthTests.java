package com.cerberus.avanzanaturaldentityservice;

import com.cerberus.avanzanaturaldentityservice.dto.UserDto;
import com.cerberus.avanzanaturaldentityservice.model.Role;
import com.cerberus.avanzanaturaldentityservice.security.AuthRequest;
import com.cerberus.avanzanaturaldentityservice.security.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void registration(){
        HttpEntity<UserDto> httpEntity = new HttpEntity<>(new UserDto(null,"Mike", "Thompson", "mike@gmail.com",
                "password123", false, Role.ROLE_USER));

        ResponseEntity<String> responseEntity = this.testRestTemplate.exchange(
                "http://localhost:%d/api/v1/auth/register".formatted(port),
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("The user has been created.", responseEntity.getBody());
    }

    @Test
    public void login(){
        HttpEntity<AuthRequest> httpEntity = new HttpEntity<>(new AuthRequest("mike@gmail.com", "password123"));

        ResponseEntity<JwtResponse> responseEntity = this.testRestTemplate.exchange(
                "http://localhost:%d/api/v1/auth/login".formatted(port),
                HttpMethod.POST,
                httpEntity,
                JwtResponse.class
        );

        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(responseEntity.getBody().getAccessToken());
        Assertions.assertNotNull(responseEntity.getBody().getRefreshToken());
    }
}
