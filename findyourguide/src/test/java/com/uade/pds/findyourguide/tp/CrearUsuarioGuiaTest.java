package com.uade.pds.findyourguide.tp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uade.pds.findyourguide.TestHelper;
import com.uade.pds.findyourguide.controller.dto.UsuarioDTO;
import com.uade.pds.findyourguide.enums.EstadoUsuario;
import com.uade.pds.findyourguide.enums.Idioma;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.model.user.UsuarioGuia;
import com.uade.pds.findyourguide.service.UsuarioGuiaService;
import com.uade.pds.findyourguide.service.UsuarioService;
import com.uade.pds.findyourguide.tasks.ValidarGuiasTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CrearUsuarioGuiaTest {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioGuiaService usuarioGuiaService;

    @Autowired
    private ValidarGuiasTask validarGuiasTask;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void registar_usuario_guia() throws JsonProcessingException {
        TestHelper testHelper = TestHelper.getInstance(restTemplate, port);

        long idUsuario = crearUsuarioGuia();

        verificarEstadoPendiente(idUsuario);

        testHelper.loguearUsuario("guiaTest@test.com", "abc123");

        subirCredencial(idUsuario);

        correrTaskValidacion();

        validarEstadoActivo(idUsuario);
    }

    private long crearUsuarioGuia() throws JsonProcessingException {
        TestHelper testHelper = TestHelper.getInstance(restTemplate, port);

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setEmail("guiaTest@test.com");
        usuarioDTO.setDni("45486763");
        usuarioDTO.setNombre("Hugo");
        usuarioDTO.setApellido("Boss");
        usuarioDTO.setSexo("Male");
        usuarioDTO.setNumTelefono("1543506745");
        usuarioDTO.setImgPerfil("img.jpg");
        usuarioDTO.setPassword("abc123");
        usuarioDTO.setIdiomas(Arrays.asList(Idioma.ALEMAN, Idioma.ESPANIOL));

        String ruta = "/registrar/guia?tipoRegistro=EMAIL_PASSWORD";
        ResponseEntity<Void> responseRegister = testHelper.sendRequest(ruta, HttpMethod.POST, usuarioDTO, Void.class);
        assertEquals(HttpStatus.OK, responseRegister.getStatusCode());

        Optional<Usuario> optUsuario = usuarioService.findUserByEmail("guiaTest@test.com");
        assertTrue(optUsuario.isPresent());

        return optUsuario.get().getId();
    }

    private void verificarEstadoPendiente(long idUsuario) throws JsonProcessingException {
        Optional<UsuarioGuia> optUsuarioGuia = usuarioGuiaService.buscarUsuarioGuia(idUsuario);

        assertTrue(optUsuarioGuia.isPresent());

        UsuarioGuia usuarioGuia = optUsuarioGuia.get();

        assertNull(usuarioGuia.getImgCredencial());
        assertEquals(EstadoUsuario.PENDIENTE_VALIDACION, usuarioGuia.getEstado());
    }

    private void subirCredencial(long idUsuario) throws JsonProcessingException {
        TestHelper testHelper = TestHelper.getInstance(restTemplate, port);

        String imgUrl = "imgCredencial.jpg";

        String ruta = "/subirCredencial?imgUrl=%s".formatted(imgUrl);
        ResponseEntity<Void> responseRegister = testHelper.sendRequest(ruta, HttpMethod.PUT, null, Void.class);
        assertEquals(HttpStatus.OK, responseRegister.getStatusCode());

        UsuarioGuia usuarioGuia = usuarioGuiaService.buscarUsuarioGuia(idUsuario).get();
        assertEquals(imgUrl, usuarioGuia.getImgCredencial());
    }

    private void correrTaskValidacion() {
        validarGuiasTask.validarGuias();
    }

    private void validarEstadoActivo(long idUsuario) {
        UsuarioGuia usuarioGuia = usuarioGuiaService.buscarUsuarioGuia(idUsuario).get();
        assertEquals(EstadoUsuario.ACTIVO, usuarioGuia.getEstado());
    }
}
