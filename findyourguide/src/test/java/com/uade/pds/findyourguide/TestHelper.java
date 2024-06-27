package com.uade.pds.findyourguide;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uade.pds.findyourguide.controller.dto.UsuarioDTO;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHelper {
    private final HttpHeaders headers;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TestRestTemplate restTemplate;
    private final int port;

    private static TestHelper instance;

    public static synchronized TestHelper getInstance(TestRestTemplate restTemplate, int port) {
        if (instance == null) {
            instance = new TestHelper(restTemplate, port);
        }
        return instance;
    }

    private TestHelper(TestRestTemplate restTemplate, int port) {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        this.port = port;
        this.restTemplate = restTemplate;
    }

    private String createURLWithPort(String route) {
        return "http://localhost:" + port + route;
    }

    public <T, R> ResponseEntity<R> sendRequest(String ruta, HttpMethod httpMethod, T entidad, Class<R> responseType) throws JsonProcessingException {

        HttpEntity<String> entity;
        if (entidad != null) {
            String strEntity = objectMapper.writeValueAsString(entidad);
            entity = new HttpEntity<>(strEntity, headers);
        }
        else {
            entity = new HttpEntity<>(headers);
        }

        return restTemplate.exchange(createURLWithPort(ruta), httpMethod, entity, responseType);
    }

    public void loguearUsuario(String email, String password) throws JsonProcessingException {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail(email);
        dto.setPassword(password);

        String route = "/login";
        //ResponseEntity<String> responseRegister = restTemplate.exchange(createURLWithPort(route), HttpMethod.POST, entity, String.class);
        ResponseEntity<String> responseRegister = sendRequest(route, HttpMethod.POST, dto, String.class);
        assertEquals(HttpStatus.OK, responseRegister.getStatusCode());

        String responseToken = responseRegister.getBody();

        headers.setBearerAuth(responseToken);
    }
}
