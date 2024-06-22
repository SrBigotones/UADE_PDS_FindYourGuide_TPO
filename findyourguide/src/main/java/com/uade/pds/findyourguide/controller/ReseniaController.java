package com.uade.pds.findyourguide.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uade.pds.findyourguide.controller.dto.ReseniaDTO;
import com.uade.pds.findyourguide.model.Resenia;
import com.uade.pds.findyourguide.service.ReseniasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "/resenia")
public class ReseniaController {

    @Autowired private ReseniasService reseniasService;

    @Autowired private ObjectMapper objectMapper = new ObjectMapper();


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
