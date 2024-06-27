package com.uade.pds.findyourguide.tp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uade.pds.findyourguide.TestHelper;
import com.uade.pds.findyourguide.controller.dto.UsuarioDTO;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CrearUsuarioTuristaTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void regitrar_usuario_turista() throws JsonProcessingException {
        TestHelper testHelper = TestHelper.getInstance(restTemplate, port);

        long idUsuario = crearUsuarioTurista();

        testHelper.loguearUsuario("turistaTest@test.com", "abc123");
    }

    private long crearUsuarioTurista() throws JsonProcessingException {
        TestHelper testHelper = TestHelper.getInstance(restTemplate, port);

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setEmail("turistaTest@test.com");
        usuarioDTO.setDni("45486763");
        usuarioDTO.setNombre("Hugo turista");
        usuarioDTO.setApellido("Boss");
        usuarioDTO.setSexo("Male");
        usuarioDTO.setNumTelefono("1543506745");
        usuarioDTO.setImgPerfil("img.jpg");
        usuarioDTO.setPassword("abc123");

        String ruta = "/registrar/guia?tipoRegistro=EMAIL_PASSWORD";
        ResponseEntity<Void> responseRegister = testHelper.sendRequest(ruta, HttpMethod.POST, usuarioDTO, Void.class);
        assertEquals(HttpStatus.OK, responseRegister.getStatusCode());

        Optional<Usuario> optUsuario = usuarioService.findUserByEmail("turistaTest@test.com");
        assertTrue(optUsuario.isPresent());

        return optUsuario.get().getId();
    }

}
