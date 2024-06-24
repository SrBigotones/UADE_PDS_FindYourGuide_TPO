package com.uade.pds.findyourguide.repository;


import com.uade.pds.findyourguide.model.trofeo.TipoTrofeo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoTrofeoRepository extends JpaRepository<TipoTrofeo,Long> {


    Optional<TipoTrofeo> findByNombreTrofeo(String nombre);
}
