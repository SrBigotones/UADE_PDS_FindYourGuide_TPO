package com.uade.pds.findyourguide.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.uade.pds.findyourguide.enums.EstadoContrato;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ContratoDTO {
    private Long idContrato;
    private String fechaIni;
    private String fechaFin;
    private double importe;
    private EstadoContrato estado;
    private ServicioGuiaDTO servicio;
    private UsuarioDTO usuarioContratante;
    private UsuarioDTO usuarioGuia;
}
