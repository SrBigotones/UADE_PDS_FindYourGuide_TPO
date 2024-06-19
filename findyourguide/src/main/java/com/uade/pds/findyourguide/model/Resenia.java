package com.uade.pds.findyourguide.model;

import jakarta.persistence.*;

@Entity
public class Resenia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn
    private Usuario usuarioTurista;
    @ManyToOne
    @JoinColumn
    private ServicioGuia servicioContratado;
    @Column
    private short calificacion;
    @Column
    private String comentario;
}
