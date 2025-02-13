package com.uade.pds.findyourguide.repository;

import com.uade.pds.findyourguide.model.trofeo.TipoTrofeo;
import com.uade.pds.findyourguide.model.trofeo.Trofeo;
import com.uade.pds.findyourguide.model.user.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrofeoRepository extends JpaRepository<Trofeo,Long> {
    List<Trofeo> findTrofeosByUsuarioGanador(Usuario usuario);

    Optional<Trofeo> findByTrofeoOtorgadoAndUsuarioGanador(TipoTrofeo tipo_trofeo, Usuario usuario_ganador);
}
