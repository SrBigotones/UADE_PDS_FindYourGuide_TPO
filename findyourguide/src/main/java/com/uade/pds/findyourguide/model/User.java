package com.uade.pds.findyourguide.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false, length = 512)
    private String name;

    @Column(name = "surname", nullable = false, length = 512)
    private String surname;

    @Column(name = "sex", nullable = false, length = 512)
    private String sex;

    @Column(name = "dni", nullable = false, length = 512)
    private String dni;

    @Column(name = "email", nullable = false, length = 512)
    private String email;

    @Column(name = "phoneNumber", nullable = false, length = 512)
    private String phoneNumber;

    @Column(name = "perfilImg", nullable = false, length = 512)
    private String perfilImg;

    @Column(name = "password", nullable = false, length = 512)
    private String password;
}
