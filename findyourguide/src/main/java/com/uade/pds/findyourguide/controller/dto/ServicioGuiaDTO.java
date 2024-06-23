package com.uade.pds.findyourguide.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uade.pds.findyourguide.enums.TipoServicio;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ServicioGuiaDTO {
    private long id;
    private long guiaId;
    private double precio;
    private String nombre;
    private String descripcion;
    private TipoServicio tipoServicio;
}

