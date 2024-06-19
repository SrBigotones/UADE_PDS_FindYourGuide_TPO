package com.uade.pds.findyourguide.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.uade.pds.findyourguide.controller.dto.UserDTO;
import com.uade.pds.findyourguide.repository.UserRepository;
import com.uade.pds.findyourguide.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTests {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    private static HttpHeaders headers;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }
    private String createURLWithPort(String route) {
        return "http://localhost:" + port + route;
    }
    @Test
    public void UserController_RegisterUser_ReturnsStatusOK() throws Exception {

        UserDTO userDTO = new UserDTO();

        userDTO.setEmail("test@test.com");
        userDTO.setDni("111111");
        userDTO.setName("Pepe");
        userDTO.setSurname("Pelusin");
        userDTO.setSex("Male");
        userDTO.setPhoneNumber("11111");
        userDTO.setPerfilImg("img.jpg");
        userDTO.setPassword("abc123");

        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(userDTO), headers);
        ResponseEntity<Void> response = restTemplate.exchange(createURLWithPort("/register"), HttpMethod.POST, entity, new ParameterizedTypeReference<Void>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void UserController_RegisterThenLoginUser_ReturnsStringToken() throws Exception {


        UserDTO userDTO = new UserDTO();

        userDTO.setEmail("test@test.com");
        userDTO.setDni("111111");
        userDTO.setName("Pepe");
        userDTO.setSurname("Pelusin");
        userDTO.setSex("Male");
        userDTO.setPhoneNumber("11111");
        userDTO.setPerfilImg("img.jpg");
        userDTO.setPassword("abc123");

        //Register the user
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(userDTO), headers);
        ResponseEntity<Void> responseRegister = restTemplate.exchange(createURLWithPort("/register"), HttpMethod.POST, entity, new ParameterizedTypeReference<Void>(){});
        assertEquals(responseRegister.getStatusCode(), HttpStatus.OK);


        //Login the user
        entity = new HttpEntity<>(objectMapper.writeValueAsString(userDTO), headers);
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/login"), HttpMethod.POST, entity, new ParameterizedTypeReference<String>(){});

        assert response.hasBody();
        assert !Objects.requireNonNull(response.getBody()).isEmpty();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}
