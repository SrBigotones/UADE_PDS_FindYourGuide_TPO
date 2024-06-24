package com.uade.pds.findyourguide.controller.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TrofeoDTO {
    private Long id;
    private UsuarioDTO usuarioGanador;
    private TipoTrofeoDTO trofeoOtorgado;
}
