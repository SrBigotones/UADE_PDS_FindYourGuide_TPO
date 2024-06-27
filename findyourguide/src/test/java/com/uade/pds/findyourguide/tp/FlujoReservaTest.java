package com.uade.pds.findyourguide.tp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uade.pds.findyourguide.TestHelper;
import com.uade.pds.findyourguide.controller.dto.*;
import com.uade.pds.findyourguide.enums.EstadoContrato;
import com.uade.pds.findyourguide.enums.EstadoFactura;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FlujoReservaTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void flujo_reserva() throws JsonProcessingException {
        TestHelper testHelper = TestHelper.getInstance(restTemplate, port);

        // TURISTA
        testHelper.loguearUsuario("turista@gmail.com", "pass1");

        List<GuiaDTO> guias = buscarGuias();

        ContratoDTO contratoDTO = solicitarServicio(guias.get(0));

        // GUIA
        testHelper.loguearUsuario("pepe@pepe.com", "pass1");

        long idContrato = buscarContratoComoGuia();

        aceptarContrato(idContrato);

        // TURISTA
        testHelper.loguearUsuario("turista@gmail.com", "pass1");

        long idFactura = buscarFactura(0);

        abonarFactura(idFactura);

        // GUIA
        testHelper.loguearUsuario("pepe@pepe.com", "pass1");

        idContrato = buscarContratoComoGuia();

        concluirViaje(idContrato);

        // TURISTA
        testHelper.loguearUsuario("turista@gmail.com", "pass1");

        idFactura = buscarFactura(1);

        abonarFactura(idFactura);

        dejarResenia();
    }

    private List<GuiaDTO> buscarGuias() throws JsonProcessingException {
        TestHelper testHelper = TestHelper.getInstance(restTemplate, port);

        // TODO usar filtros
        String ruta = "/guia/buscarAll";
        ResponseEntity<List<GuiaDTO>> responseGuias = testHelper.sendRequest(ruta, HttpMethod.GET, null, new ParameterizedTypeReference<List<GuiaDTO>>(){});
        assertEquals(HttpStatus.OK, responseGuias.getStatusCode());
        assertTrue(responseGuias.getBody().size() > 0);

        return responseGuias.getBody();
    }

    private ContratoDTO solicitarServicio(GuiaDTO guiaDTO) throws JsonProcessingException {
        TestHelper testHelper = TestHelper.getInstance(restTemplate, port);

        final long idUsuarioGuia = guiaDTO.getId();
        final long idServicioGuia = guiaDTO.getServicios().get(0).getId();

        ContratoDTO contratoDTO = new ContratoDTO();
        contratoDTO.setFechaIni("2024-07-04");
        contratoDTO.setFechaFin("2024-07-18");

        ServicioGuiaDTO servicioGuiaDTO = new ServicioGuiaDTO();
        servicioGuiaDTO.setId(idServicioGuia);
        contratoDTO.setServicio(servicioGuiaDTO);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(idUsuarioGuia);
        contratoDTO.setUsuarioGuia(usuarioDTO);

        ResponseEntity<ContratoDTO> responseContrato = testHelper.sendRequest("/contrato", HttpMethod.POST, contratoDTO, ContratoDTO.class);
        assertEquals(HttpStatus.OK, responseContrato.getStatusCode());

        ContratoDTO contratoDTOResult = responseContrato.getBody();

        assertEquals(EstadoContrato.RESERVA, contratoDTOResult.getEstado());

        return contratoDTOResult;
    }

    private long buscarContratoComoGuia() throws JsonProcessingException {
        TestHelper testHelper = TestHelper.getInstance(restTemplate, port);
        
        ResponseEntity<List<ContratoDTO>> responseContratos = testHelper.sendRequest("/contrato/guia", HttpMethod.GET, null, new ParameterizedTypeReference<List<ContratoDTO>>() {});
        assertEquals(HttpStatus.OK, responseContratos.getStatusCode());
        List<ContratoDTO> contratos = responseContratos.getBody();

        assertTrue(contratos.size() > 0);
        assertEquals(2, contratos.get(0).getUsuarioContratante().getId());

        return contratos.get(0).getIdContrato();
    }

    private void aceptarContrato(long idContrato) throws JsonProcessingException {
        TestHelper testHelper = TestHelper.getInstance(restTemplate, port);

        ResponseEntity<ContratoDTO> responseContrato = testHelper.sendRequest("/contrato/%s/confirmar".formatted(idContrato), HttpMethod.POST, null, ContratoDTO.class);
        assertEquals(HttpStatus.OK, responseContrato.getStatusCode());

        ContratoDTO contrato = responseContrato.getBody();
        assertEquals(EstadoContrato.ACEPTADO, contrato.getEstado());
    }

    private long buscarFactura(int indiceFactura) throws JsonProcessingException {
        TestHelper testHelper = TestHelper.getInstance(restTemplate, port);

        ResponseEntity<List<FacturaDTO>> responseFacturas = testHelper.sendRequest("/factura", HttpMethod.GET, null, new ParameterizedTypeReference<List<FacturaDTO>>() {});
        assertEquals(HttpStatus.OK, responseFacturas.getStatusCode());

        List<FacturaDTO> facturas = responseFacturas.getBody();
        assertTrue(facturas.size() > 0);
        FacturaDTO facturaDTO = facturas.get(indiceFactura);
        assertEquals(EstadoFactura.PENDIENTE, facturaDTO.getEstadoFactura());
        assertEquals(2, facturaDTO.getIdUsuario());

        return facturaDTO.getId();
    }

    private void abonarFactura(long idFactura) throws JsonProcessingException {
        TestHelper testHelper = TestHelper.getInstance(restTemplate, port);

        ResponseEntity<FacturaDTO> responseFactura = testHelper.sendRequest("/factura/%s/abonar".formatted(idFactura), HttpMethod.POST, null, new ParameterizedTypeReference<FacturaDTO>() {});
        assertEquals(HttpStatus.OK, responseFactura.getStatusCode());

        FacturaDTO factura = responseFactura.getBody();
        assertNotNull(factura);
        assertEquals(EstadoFactura.ABONADO, factura.getEstadoFactura());
    }

    private void concluirViaje(long idContrato) throws JsonProcessingException {
        TestHelper testHelper = TestHelper.getInstance(restTemplate, port);

        ResponseEntity<ContratoDTO> responseContrato = testHelper.sendRequest("/contrato/%s/concluir".formatted(idContrato), HttpMethod.POST, null, ContratoDTO.class);
        assertEquals(HttpStatus.OK, responseContrato.getStatusCode());

        ContratoDTO contrato = responseContrato.getBody();
        assertEquals(EstadoContrato.CONCLUIDO, contrato.getEstado());
    }

    private void dejarResenia() throws JsonProcessingException {
        TestHelper testHelper = TestHelper.getInstance(restTemplate, port);

        ServicioGuiaDTO servDto = new ServicioGuiaDTO();
        servDto.setId(1);

        short calificacion = 5;

        ReseniaDTO reseniaDTO = new ReseniaDTO();
        reseniaDTO.setServicioContratado(servDto);
        reseniaDTO.setCalificacion(calificacion);
        reseniaDTO.setComentario("Estuvo excelenteeeeeeeee");

        ResponseEntity<ReseniaDTO> responseResenia = testHelper.sendRequest("/resenia/publicar", HttpMethod.POST, reseniaDTO, ReseniaDTO.class);
        assertEquals(HttpStatus.OK, responseResenia.getStatusCode());

        ReseniaDTO resenia = responseResenia.getBody();
        assertEquals(reseniaDTO.getComentario(), resenia.getComentario());
        assertEquals(1, resenia.getUsuarioGuia().getId());

    }
}

