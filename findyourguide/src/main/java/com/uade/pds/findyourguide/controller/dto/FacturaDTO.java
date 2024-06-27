package com.uade.pds.findyourguide.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uade.pds.findyourguide.enums.EstadoFactura;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FacturaDTO {
    private long id;
    private EstadoFactura estadoFactura;
    private long idUsuario;
    private String nombreServicio;
    private String proposito;
    private LocalDateTime creado;
    private LocalDateTime pagado;
    private double importe;

}
