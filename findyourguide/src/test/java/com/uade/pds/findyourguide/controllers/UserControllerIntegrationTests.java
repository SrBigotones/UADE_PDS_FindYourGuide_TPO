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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTests {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    private static HttpHeaders headers;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }
    private String createURLWithPort() {
        return "http://localhost:" + port + "/register";
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
        ResponseEntity<Void> response = restTemplate.exchange(createURLWithPort(), HttpMethod.POST, entity, new ParameterizedTypeReference<Void>(){});
        
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}
