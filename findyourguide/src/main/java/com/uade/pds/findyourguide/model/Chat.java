package com.uade.pds.findyourguide.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String canalSendBird;
}
