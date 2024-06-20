package com.uade.pds.findyourguide.controller.dto;

import com.uade.pds.findyourguide.enums.EstrategiaRegistro;
import com.uade.pds.findyourguide.enums.TipoUsuario;
import lombok.Data;

@Data
public class UsuarioDTO {
    private long id;
    private String nombre;
    private String apellido;
    private String sexo;
    private String dni;
    private String email;
    private String numTelefono;
    private String imgPerfil;
    private String password;
    private TipoUsuario tipo;

}
