package com.uade.pds.findyourguide.model;

import com.uade.pds.findyourguide.enums.TipoServicio;
import com.uade.pds.findyourguide.model.user.UsuarioGuia;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ServicioGuia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre;
    @Column
    private String descripcion;
    @Column
    private double precio;
    @Column
    private TipoServicio tipoServicio;
    @Column
    private Long cupo;

    @ManyToOne
    private CiudadPais ciudadPais;

    @Column
    private long guia_id;
}
