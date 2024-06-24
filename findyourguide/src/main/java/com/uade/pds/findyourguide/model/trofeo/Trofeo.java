package com.uade.pds.findyourguide.model.trofeo;

import com.uade.pds.findyourguide.model.user.Usuario;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Trofeo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn
    private Usuario usuarioGanador;

    @ManyToOne
    @JoinColumn
    private TipoTrofeo trofeoOtorgado;
}
