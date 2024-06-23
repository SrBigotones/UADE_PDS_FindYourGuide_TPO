package com.uade.pds.findyourguide.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uade.pds.findyourguide.controller.dto.ReseniaDTO;
import com.uade.pds.findyourguide.model.Resenia;
import com.uade.pds.findyourguide.model.contrato.Contrato;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.service.ContratoService;
import com.uade.pds.findyourguide.service.ReseniasService;
import com.uade.pds.findyourguide.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController(value = "/resenia")
@EnableScheduling
public class ReseniaController {

    @Autowired private ReseniasService reseniasService;

    @Autowired private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private UsuarioService usuarioService;

    private static final long DIAS_FINALIZADO = 5;

    // @Scheduled(fixedRate = DIAS_FINALIZADO * 24 * 60 * 60 * 1000L)
    @Scheduled(fixedRate = 5000)
    public void buscarPendientesResenias() {
        System.out.println("Se corrio la funcion. (:");
        LocalDate fechaObjetivo = LocalDate.now().minusDays(DIAS_FINALIZADO);
        List<Contrato> contratosEncontrados = contratoService.obtenerContratosConFechaFin(fechaObjetivo);
        System.out.println(contratosEncontrados.isEmpty());
        for (Contrato c : contratosEncontrados) {
            Optional<Usuario> usuarioEncontrado = usuarioService.findUserById(c.getUsuarioContratante().getId());
            // ACA LE AVISAMOS AL MAIL POR ACCION DIVINA
            usuarioEncontrado.ifPresent(usuario -> System.out.println(usuario.getEmail()));
        }
    }


    @GetMapping(value = "/guia")
    public ResponseEntity<List<ReseniaDTO>> obtenerReseniaPorGuia(long id){
        return null;
    }

    @PostMapping(value = "/publicar")
    public ResponseEntity<ReseniaDTO> escribirResenia(ReseniaDTO reseniaDTO){
        return null;
    }



    private ReseniaDTO toDTO(Resenia resenia){
        ReseniaDTO reseniaDTO = objectMapper.convertValue(resenia, ReseniaDTO.class);
        return reseniaDTO;
    }


    private Resenia toResenia(ReseniaDTO reseniaDTO){
        Resenia resenia = objectMapper.convertValue(reseniaDTO, Resenia.class);
        return resenia;
    }
}
