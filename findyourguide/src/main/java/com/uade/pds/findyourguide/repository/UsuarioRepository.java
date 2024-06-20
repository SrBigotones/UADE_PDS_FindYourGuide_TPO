package com.uade.pds.findyourguide.repository;

import com.uade.pds.findyourguide.model.user.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

//    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> findUserByEmail(@Param("email") String email);

}
