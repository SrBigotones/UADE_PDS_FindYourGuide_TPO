package com.uade.pds.findyourguide.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.uade.pds.findyourguide.model.trofeo.CondicionTrofeo;
import com.uade.pds.findyourguide.model.trofeo.Trofeo;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.observer.IObservable;
import com.uade.pds.findyourguide.observer.IObserver;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Component
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Resenia implements IObservable{

    @Transient
    @JsonIgnore
    private static List<CondicionTrofeo> observadores = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn
    private Usuario usuarioTurista;
    @ManyToOne
    @JoinColumn
    private Usuario usuarioGuia;
    @ManyToOne
    @JoinColumn
    private ServicioGuia servicioContratado;
    @Column
    private short calificacion;
    @Column
    private String comentario;

    @Override
    public void agregar(IObserver observer) {
        Resenia.observadores.add((CondicionTrofeo) observer);
    }

    @Override
    public void eliminar(IObserver observer) {
        Resenia.observadores.remove((CondicionTrofeo) observer);
    }

    @Override
    public void notificar() {
        for(CondicionTrofeo condicionTrofeo: Resenia.observadores){
            condicionTrofeo.serNotificado(this);
        }
    }
}
