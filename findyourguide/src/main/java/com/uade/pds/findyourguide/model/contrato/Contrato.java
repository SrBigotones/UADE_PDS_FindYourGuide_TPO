package com.uade.pds.findyourguide.model.contrato;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.uade.pds.findyourguide.enums.EstadoContrato;
import com.uade.pds.findyourguide.model.ServicioGuia;
import com.uade.pds.findyourguide.model.contrato.state.*;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.model.user.UsuarioGuia;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
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
    @Column
    private EstadoContrato estadoContrato;

    @JsonIgnore
    @Transient
    private IStateContrato stateContrato;


    public IStateContrato getStateContrato(){
        if(this.stateContrato == null){
            this.stateContrato = StateContratoFactory.crearState(this.estadoContrato);
        }
        return this.stateContrato;
    }

    public void cambiarEstado(IStateContrato estado){
        this.estadoContrato = this.getEstadoContrato();
    }
}
