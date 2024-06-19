package com.uade.pds.findyourguide.controller.dto;

import com.uade.pds.findyourguide.enums.EstrategiaRegistro;
import com.uade.pds.findyourguide.enums.TipoUsuario;
import lombok.Data;

@Data
public class RegistroDTO {
    private String email;
    private String password;
    private String apiServicio;
    private TipoUsuario tipoUsuario;
    private String acreditacion;
    private String imgCredencial;
    private String nombre;
    private String apellido;
    private String sexo;
    private String dni;
    private String numTelefono;
    private EstrategiaRegistro tipoRegistro;
}
