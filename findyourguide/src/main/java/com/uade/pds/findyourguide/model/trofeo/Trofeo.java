package com.uade.pds.findyourguide.model.trofeo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.observer.IObservable;
import com.uade.pds.findyourguide.observer.IObserver;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
