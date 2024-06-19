package com.uade.pds.findyourguide.controller.dto;

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
}
