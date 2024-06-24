package com.uade.pds.findyourguide.controller;


import com.uade.pds.findyourguide.controller.dto.ReseniaDTO;
import com.uade.pds.findyourguide.controller.dto.TrofeoDTO;
import com.uade.pds.findyourguide.model.Resenia;
import com.uade.pds.findyourguide.model.trofeo.Trofeo;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.service.TrofeoService;
import com.uade.pds.findyourguide.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trofeo")
public class TrofeoController {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TrofeoService trofeoService;



    public void verificarPremios(Resenia resenia) {
        Usuario usuario_turista = resenia.getUsuarioTurista();
        Optional<Usuario> usuario_guia = usuarioService.findUserById(resenia.getServicioContratado().getGuia_id());
        Trofeo trofeoExito = trofeoService.ganoTrofeosExito(usuario_guia.get());
        Trofeo trofeoResenias = trofeoService.ganoTrofeosResenias(usuario_turista);

        if (trofeoExito != null) {
            System.out.printf("El usuario %s ha ganado un trofeo al exito!",trofeoExito.getUsuarioGanador().getNombre()); // ESTO SERIA UNA PUSH
        }
        if (trofeoResenias != null) {
            System.out.printf("El usuario %s ha ganado un trofeo a la resenia!",trofeoResenias.getUsuarioGanador().getNombre()); // ESTO SERIA UNA PUSH AL USUARIO
        }
    }


    @GetMapping(value = "/{usu_id}")
    public ResponseEntity<List<TrofeoDTO>> getTrofeos(@PathVariable("usu_id") long usuario_id) {

        Optional<Usuario> usuarioEncontrado = usuarioService.findUserById(usuario_id);
        if (usuarioEncontrado.isEmpty())
            return  new ResponseEntity("El usuario no existe!", HttpStatus.NO_CONTENT);
        List<Trofeo> trofeosEncontrados = trofeoService.buscarTrofeos(usuarioEncontrado.get());
        List<TrofeoDTO> trofeosDTO = trofeosEncontrados.stream()
                .map(trofeo -> {
                    return objectMapper.convertValue(trofeo, TrofeoDTO.class);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(trofeosDTO);

    }



    private TrofeoDTO toDTO(Trofeo trofeo){
        TrofeoDTO trofeoDTO = objectMapper.convertValue(trofeo,TrofeoDTO.class);
        return trofeoDTO;
    }


    private Trofeo toTrofeo(TrofeoDTO trofeoDTO){
        Trofeo trofeo = objectMapper.convertValue(trofeoDTO, Trofeo.class);
        return trofeo;
    }

}
