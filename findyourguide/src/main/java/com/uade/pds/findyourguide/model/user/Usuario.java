package com.uade.pds.findyourguide.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uade.pds.findyourguide.enums.EstadoUsuario;
import com.uade.pds.findyourguide.model.Resenia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre", nullable = false, length = 512)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 512)
    private String apellido;

    @Column(name = "sexo", nullable = false, length = 512)
    private String sexo;

    @Column(name = "dni", nullable = false, length = 512)
    private String dni;

    @Column(name = "email", nullable = false, length = 512, unique = true)
    private String email;

    @Column(name = "numTelefono", nullable = false, length = 512)
    private String numTelefono;

    @Column(name = "imgPerfil", nullable = false, length = 512)
    private String imgPerfil;

    @Column(name = "password", nullable = false, length = 512)
    private String password;

    @Column(name = "estado", nullable = false, length = 512)
    @Enumerated(EnumType.STRING)
    private EstadoUsuario estado;

    @OneToMany
    @JoinColumn(name = "usuario_turista_id")
    @JsonIgnore
    private List<Resenia> reseniaList;

    public boolean isActive() {
        return estado.equals(EstadoUsuario.ACTIVO);
    }
}
