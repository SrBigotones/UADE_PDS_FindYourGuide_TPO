package com.uade.pds.findyourguide;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uade.pds.findyourguide.controller.dto.UsuarioDTO;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class TestHelper {
    private static TestHelper instance;
    private final HttpHeaders headers;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TestRestTemplate restTemplate;
    private final int port;

    private TestHelper(TestRestTemplate restTemplate, int port) {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        this.port = port;
        this.restTemplate = restTemplate;
    }

    public static synchronized TestHelper getInstance(TestRestTemplate restTemplate, int port) {
        if (instance == null) {
            instance = new TestHelper(restTemplate, port);
        }
        return instance;
    }

    private String createURLWithPort(String route) {
        return "http://localhost:" + port + route;
    }

    private <T> HttpEntity<String> createEntity(T entidad) throws JsonProcessingException {
        HttpEntity<String> entity;
        if (entidad != null) {
            String strEntity = objectMapper.writeValueAsString(entidad);
            entity = new HttpEntity<>(strEntity, headers);
        } else {
            entity = new HttpEntity<>(headers);
        }
        return entity;
    }

    public <T, R> ResponseEntity<R> sendRequest(String ruta, HttpMethod httpMethod, T entidad, Class<R> responseType) throws JsonProcessingException {
        return restTemplate.exchange(createURLWithPort(ruta), httpMethod, createEntity(entidad), responseType);
    }

    public <T, R> ResponseEntity<R> sendRequest(String ruta, HttpMethod httpMethod, T entidad, ParameterizedTypeReference<R> responseType) throws JsonProcessingException {
        return restTemplate.exchange(createURLWithPort(ruta), httpMethod, createEntity(entidad), responseType);
    }

    public ResponseEntity<String> loguearUsuario(String email, String password) throws JsonProcessingException {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail(email);
        dto.setPassword(password);

        String route = "/login";
        ResponseEntity<String> responseRegister = sendRequest(route, HttpMethod.POST, dto, String.class);

        String responseToken = responseRegister.getBody();

        headers.setBearerAuth(responseToken);

        return responseRegister;
    }
}
