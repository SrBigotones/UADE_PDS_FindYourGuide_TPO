package com.uade.pds.findyourguide.model.premio;

import com.uade.pds.findyourguide.model.ServicioGuia;
import com.uade.pds.findyourguide.model.user.Usuario;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Premio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn
    private Usuario usuarioTurista;

    @Column
    private String calificacion;
}
