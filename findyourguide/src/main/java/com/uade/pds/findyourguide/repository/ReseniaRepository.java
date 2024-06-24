package com.uade.pds.findyourguide.repository;

import com.uade.pds.findyourguide.model.Resenia;
import com.uade.pds.findyourguide.model.ServicioGuia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReseniaRepository extends JpaRepository<Resenia, Long> {


    Optional<List<Resenia>> findAllByServicioContratado(ServicioGuia servicioContratado);

    @Query("SELECT r FROM Resenia r JOIN r.servicioContratado sg WHERE sg.guia_id = :guiaId")
    List<Resenia> findReseniasByServicioContratadoGuiaId(@Param("guiaId") long guiaId);
}
