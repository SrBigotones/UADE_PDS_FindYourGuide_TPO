package com.uade.pds.findyourguide.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uade.pds.findyourguide.enums.EstadoContrato;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OperacionContratoDTO {

    private long idContrato;
    private Double importe;
}
