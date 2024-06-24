package com.uade.pds.findyourguide.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uade.pds.findyourguide.enums.EstrategiaRegistro;
import com.uade.pds.findyourguide.enums.Idioma;
import com.uade.pds.findyourguide.enums.TipoUsuario;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String sexo;
    private String dni;
    private String email;
    private String numTelefono;
    private String imgPerfil;
    private String password;
    private TipoUsuario tipo;

    private List<Idioma> idiomas;

}
