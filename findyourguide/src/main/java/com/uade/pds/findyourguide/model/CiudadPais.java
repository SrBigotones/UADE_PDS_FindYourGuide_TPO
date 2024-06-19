package com.uade.pds.findyourguide.model;

import jakarta.persistence.*;

@Entity
public class CiudadPais {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String ciudad;
    @Column
    private String pais;
}
