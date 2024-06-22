package com.uade.pds.findyourguide.model;

import com.uade.pds.findyourguide.enums.TipoServicio;
import com.uade.pds.findyourguide.model.user.UsuarioGuia;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ServicioGuia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String nombre;
    @Column
    private String descripcion;
    @Column
    private double precio;
    @Column
    private TipoServicio tipoServicio;


    @Column
    private long guia_id;
}
