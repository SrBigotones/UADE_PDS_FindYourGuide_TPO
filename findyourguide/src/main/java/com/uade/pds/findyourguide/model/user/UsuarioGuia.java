package com.uade.pds.findyourguide.model.user;

import com.uade.pds.findyourguide.enums.Idioma;
import com.uade.pds.findyourguide.model.CiudadPais;
import com.uade.pds.findyourguide.model.ServicioGuia;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuarios_guia")
public class UsuarioGuia extends Usuario {

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
