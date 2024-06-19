package com.uade.pds.findyourguide.model;

import com.uade.pds.findyourguide.enums.TipoServicio;
import jakarta.persistence.*;

@Entity
public class ServicioGuia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String nombre;
    @Column
    private String descripcion;
    @Column
    private TipoServicio tipoServicio;
}
