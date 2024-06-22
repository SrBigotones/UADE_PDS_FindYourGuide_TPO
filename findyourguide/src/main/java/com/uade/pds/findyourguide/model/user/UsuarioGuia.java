package com.uade.pds.findyourguide.model.user;

import com.uade.pds.findyourguide.enums.Idioma;
import com.uade.pds.findyourguide.model.CiudadPais;
import com.uade.pds.findyourguide.model.ServicioGuia;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "usuarios_guia")
public class UsuarioGuia extends Usuario {

    @Column
    private String imgCredencial;
    @Column
    private int puntuacion;
    @Column
    private List<Idioma> idiomas;

    @OneToMany
    @JoinColumn(name = "guia_id")
    private List<ServicioGuia> listServicios;

    @ManyToMany
    private List<CiudadPais> listaCiudadesActivo;


}
