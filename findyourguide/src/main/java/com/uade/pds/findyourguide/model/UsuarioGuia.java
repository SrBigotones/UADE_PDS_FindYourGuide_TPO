package com.uade.pds.findyourguide.model;

import com.uade.pds.findyourguide.enums.Idioma;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class UsuarioGuia extends Usuario{

    @Column
    private String imgCredencial;
    @Column
    private int puntuacion;
    @Column
    private List<Idioma> idiomas;

    @OneToMany
    @JoinColumn(name = "servicio_id")
    private Set<ServicioGuia> listServicios;
    @OneToMany
    @JoinColumn(name = "ciudadpais_id")
    private List<CiudadPais> listaCiudadesActivo;


}
