package com.uade.pds.findyourguide.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CiudadPais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String ciudad;
    @Column
    private String pais;
}
