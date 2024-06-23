package com.uade.pds.findyourguide.model;

import com.uade.pds.findyourguide.model.user.Usuario;
import jakarta.persistence.*;
import lombok.Data;

@Data
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
