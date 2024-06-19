package com.uade.pds.findyourguide.model.contrato;

import com.uade.pds.findyourguide.model.ServicioGuia;
import com.uade.pds.findyourguide.model.Usuario;
import com.uade.pds.findyourguide.model.UsuarioGuia;
import com.uade.pds.findyourguide.model.contrato.state.IStateContrato;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    @JoinColumn
    private ServicioGuia servicio;
    @ManyToOne
    @JoinColumn
    private Usuario usuarioContratante;
    @ManyToOne
    @JoinColumn
    private UsuarioGuia usuarioContratado;
    @Column
    private LocalDate fechaIni;
    @Column
    private LocalDate fechaFin;
    @Column
    private double importe;
    @Transient
    private IStateContrato estado;


    public void cambiarEstado(IStateContrato estado){
        this.estado = estado;
    }
}
