package com.uade.pds.findyourguide.tp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uade.pds.findyourguide.TestHelper;
import com.uade.pds.findyourguide.controller.dto.ReseniaDTO;
import com.uade.pds.findyourguide.controller.dto.ServicioGuiaDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrofeoTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void login() throws JsonProcessingException {
        TestHelper testHelper = TestHelper.getInstance(restTemplate, port);
        String email = "turista@gmail.com";
        String password = "pass1";
        ResponseEntity<String> loginResponse = testHelper.loguearUsuario(email, password);
        assertEquals(HttpStatus.OK,loginResponse.getStatusCode());
        String ruta = "/resenia/publicar";
        ReseniaDTO miResenia = crearResenia();

        ResponseEntity<String> crearReseniaResponse = testHelper.sendRequest(ruta,HttpMethod.POST,miResenia,String.class);
        assertEquals(HttpStatus.OK,crearReseniaResponse.getStatusCode());




//        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
//        assertNotNull(loginResponse.getBody());
    }


    public ReseniaDTO crearResenia() {
        ReseniaDTO nuevaResenia = new ReseniaDTO();
        nuevaResenia.setCalificacion((short) 5);
        nuevaResenia.setComentario("El mejor guia de zona centro");
        ServicioGuiaDTO servicioContratado = new ServicioGuiaDTO();
        servicioContratado.setId(1);
        nuevaResenia.setServicioContratado(servicioContratado);
        return nuevaResenia;
    }

}
