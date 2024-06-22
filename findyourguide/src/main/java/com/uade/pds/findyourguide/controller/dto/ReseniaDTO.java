package com.uade.pds.findyourguide.controller.dto;

import lombok.Data;

@Data
public class ReseniaDTO {
    private long id;
    private UsuarioDTO usuarioTurista;
    private ServicioGuiaDTO servicioContratado;
    private short calificacion;
    private String comentario;
}
