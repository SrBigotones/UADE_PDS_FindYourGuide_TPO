package com.uade.pds.findyourguide.controller.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TipoTrofeoDTO {
 private Long id;
 private String nombreTrofeo;
 private String descripcionTrofeo;
}
