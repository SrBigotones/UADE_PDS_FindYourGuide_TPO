package com.uade.pds.findyourguide.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uade.pds.findyourguide.controller.dto.ReseniaDTO;
import com.uade.pds.findyourguide.enums.EstadoContrato;
import com.uade.pds.findyourguide.model.Resenia;
import com.uade.pds.findyourguide.model.ServicioGuia;
import com.uade.pds.findyourguide.model.contrato.Contrato;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.repository.ServicioGuiaRepository;
import com.uade.pds.findyourguide.security.CustomUserDetails;
import com.uade.pds.findyourguide.service.ContratoService;
import com.uade.pds.findyourguide.service.ReseniasService;
import com.uade.pds.findyourguide.service.UsuarioGuiaService;
import com.uade.pds.findyourguide.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resenia")
@EnableScheduling
public class ReseniaController {

    @Autowired private ReseniasService reseniasService;

    @Autowired private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UsuarioGuiaService usuarioGuiaService;

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private UsuarioService usuarioService;


    private static final long DIAS_FINALIZADO = 5;

    //@Scheduled(fixedRate = 5000)
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void buscarPendientesResenias() {
        LocalDate fechaObjetivo = LocalDate.now().minusDays(DIAS_FINALIZADO);
        List<Contrato> contratosEncontrados = contratoService.obtenerContratosConFechaFin(fechaObjetivo);
        for (Contrato c : contratosEncontrados) {
            Optional<Usuario> usuarioEncontrado = usuarioService.findUserById(c.getUsuarioContratante().getId());
            // ACA LE AVISAMOS AL MAIL POR ACCION DIVINA
            usuarioEncontrado.ifPresent(usuario -> System.out.println(usuario.getEmail()));
        }
    }


    @GetMapping(value = "/guia/{id}")
    public ResponseEntity<List<ReseniaDTO>> obtenerReseniaPorGuia(@PathVariable long id){
        List<Resenia> reseniasEncontradas = reseniasService.obtenerReseniasDeGuia(id);
        List<ReseniaDTO> reseniasDTO = reseniasEncontradas.stream()
                .map(resenia -> {
                    return objectMapper.convertValue(resenia, ReseniaDTO.class);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(reseniasDTO);

    }


    @PostMapping(value = "/publicar")
    public ResponseEntity escribirResenia(@RequestBody ReseniaDTO reseniaDTO, Authentication authentication){
        // Nos genera la reseña
        Resenia reseniaRecibida = toResenia(reseniaDTO);
        Usuario usuario = ((CustomUserDetails) authentication.getPrincipal()).getUsuario();
        reseniaRecibida.setServicioContratado(usuarioGuiaService.obtenerServicioPorId(reseniaDTO.getServicioContratado().getId()).get());
        reseniaRecibida.setUsuarioTurista(usuario);

        // Nos fijamos si la persona que hizo la reseña tuvo un contrato con ese servicio.
        List<Contrato> contratoEncontrado = contratoService.obtenerContratoPorServicioYGuia(reseniaRecibida.getServicioContratado(),reseniaRecibida.getUsuarioTurista());
        if (contratoEncontrado.isEmpty())
            return new ResponseEntity("No se puede realizar la reseña ya que no se contrato el servicio o no fue concluido.", HttpStatus.FORBIDDEN);
        else {
            Resenia reseniaSaved = reseniasService.escribirResenia(reseniaRecibida);
            ReseniaDTO reseniaDTO1 = this.toDTO(reseniaSaved);
            return ResponseEntity.ok(reseniaDTO1);
        }
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
