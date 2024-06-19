package com.uade.pds.findyourguide.controller.dto;

import com.uade.pds.findyourguide.enums.TipoServicio;
import lombok.Data;

@Data
public class ServicioGuiaDTO {
    private double precio;
    private String nombre;
    private String descripcion;
    private TipoServicio tipoServicio;
}

