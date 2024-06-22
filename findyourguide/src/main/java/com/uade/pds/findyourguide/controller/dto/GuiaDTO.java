package com.uade.pds.findyourguide.controller.dto;

import com.uade.pds.findyourguide.enums.Idioma;
import lombok.Data;

import java.util.List;

@Data
public class GuiaDTO {
    private long id;
    private List<CiudadPaisDTO> ubicaciones;
    private String nombre;
    private String apellido;
    private List<Idioma> idiomas;
    private List<ServicioGuiaDTO> servicios;
    private int puntuacion;

}

