package com.uade.pds.findyourguide.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReseniaDTO {
    private long id;
    private UsuarioDTO usuarioTurista;
    private ServicioGuiaDTO servicioContratado;
    private short calificacion;
    private String comentario;
}
