package com.uade.pds.findyourguide.model;

import com.uade.pds.findyourguide.enums.Idioma;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class UsuarioGuia extends Usuario{

//    private Set<ServicioGuia> listServicios;
    private String imgCredencial;
    private int puntuacion;
    private List<Idioma> idiomas;
//    private List<CiudadPais> listaCiudadesActivo;


}
