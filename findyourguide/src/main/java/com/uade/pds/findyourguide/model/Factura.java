package com.uade.pds.findyourguide.model;

import com.uade.pds.findyourguide.enums.EstadoFactura;
import com.uade.pds.findyourguide.model.contrato.Contrato;
import com.uade.pds.findyourguide.model.user.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private EstadoFactura estadoFactura;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Contrato contrato;
    @Column
    private String proposito;
    @Column
    private LocalDateTime creado;
    @Column
    private LocalDateTime pagado;
    @Column
    private double importe;
}
