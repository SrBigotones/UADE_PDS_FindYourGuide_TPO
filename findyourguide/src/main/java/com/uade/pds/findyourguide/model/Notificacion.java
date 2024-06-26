package com.uade.pds.findyourguide.model;


import com.uade.pds.findyourguide.model.user.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Usuario usuario;
    @Column
    private String mensaje;
    @Column
    private LocalDateTime timestamp;
}
