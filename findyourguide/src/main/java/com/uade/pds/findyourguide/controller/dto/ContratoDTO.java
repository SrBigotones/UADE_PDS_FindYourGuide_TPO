package com.uade.pds.findyourguide.controller.dto;

import com.uade.pds.findyourguide.enums.EstadoContrato;
import lombok.Data;

import java.util.Date;

@Data
public class ContratoDTO {
    private long idContrato;
    private Date fechaIni;
    private Date fechaFin;
    private double importe;
    private EstadoContrato estado;
    private ServicioGuiaDTO servicio;
    private UserDTO usuarioContratante;
    private UserDTO usuarioGuia;
}
