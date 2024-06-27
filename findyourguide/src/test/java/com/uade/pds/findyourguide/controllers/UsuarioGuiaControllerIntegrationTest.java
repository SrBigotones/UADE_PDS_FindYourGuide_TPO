package com.uade.pds.findyourguide.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uade.pds.findyourguide.controller.dto.GuiaDTO;
import com.uade.pds.findyourguide.controller.dto.ServicioGuiaDTO;
import com.uade.pds.findyourguide.controller.dto.UsuarioDTO;
import com.uade.pds.findyourguide.enums.TipoServicio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioGuiaControllerIntegrationTest {
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


    private void createOneUsuarioGuia() throws Exception{
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setEmail("user1@gmail.com");
        usuarioDTO.setPassword("pass1");
        usuarioDTO.setDni("1111111");
        usuarioDTO.setNombre("Pepe");
        usuarioDTO.setApellido("Pepones");
        usuarioDTO.setNumTelefono("11111111");
        usuarioDTO.setSexo("M");
        usuarioDTO.setImgPerfil("IMG2.jpg");

        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(usuarioDTO),headers);
        ResponseEntity response = restTemplate.exchange(createURLWithPort("/registrar/guia?tipoRegistro=EMAIL_PASSWORD"), HttpMethod.POST, entity, new ParameterizedTypeReference<Void>(){});

    }


    @Test
    public void UsuarioGuiaController_AgregaServicioGuia_RetornaOK() throws Exception {

        this.createOneUsuarioGuia();

        ServicioGuiaDTO servicioGuiaDTO = new ServicioGuiaDTO();

        servicioGuiaDTO.setGuiaId(1);
        servicioGuiaDTO.setPrecio(222.22);
        servicioGuiaDTO.setNombre("Museos en Italia");
        servicioGuiaDTO.setDescripcion("Conoce los museos mas conocidos de todo italia en una estadia de 5 noches");
        servicioGuiaDTO.setTipoServicio(TipoServicio.TOUR_GRUPAL);


        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(servicioGuiaDTO),headers);
        ResponseEntity response = restTemplate.exchange(createURLWithPort("/guia/servicio"), HttpMethod.POST, entity, new ParameterizedTypeReference<Void>(){});


        assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void UsuarioGuiaController_AgregaServicioGuia_ConsultaGuia_RetornaSuInfoYServicios() throws Exception {

        this.createOneUsuarioGuia();

        ServicioGuiaDTO servicioGuiaDTO = new ServicioGuiaDTO();
        servicioGuiaDTO.setGuiaId(1);
        servicioGuiaDTO.setPrecio(222.22);
        servicioGuiaDTO.setNombre("Museos en Italia");
        servicioGuiaDTO.setDescripcion("Conoce los museos mas conocidos de todo italia en una estadia de 5 noches");
        servicioGuiaDTO.setTipoServicio(TipoServicio.TOUR_GRUPAL);


        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(servicioGuiaDTO),headers);
        restTemplate.exchange(createURLWithPort("/guia/servicio"), HttpMethod.POST, entity, new ParameterizedTypeReference<Void>(){});

        ResponseEntity<GuiaDTO> responseConsultaGuia = restTemplate.exchange(createURLWithPort("/guia/buscar/1"), HttpMethod.GET, new HttpEntity<>(null, headers), new ParameterizedTypeReference<GuiaDTO>(){});
        GuiaDTO guiaDTO = responseConsultaGuia.getBody();

//        System.out.println(guiaDTO);
//        System.out.println(guiaDTO.getServicios().get(0).toString());
//        System.out.println(servicioGuiaDTO);

        assert guiaDTO.getServicios().size() == 1;
        assert guiaDTO.getServicios().get(0).toString().equals(servicioGuiaDTO.toString());

    }

}
