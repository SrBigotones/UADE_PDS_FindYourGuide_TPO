package com.uade.pds.findyourguide.repository;

import com.uade.pds.findyourguide.model.ServicioGuia;
import com.uade.pds.findyourguide.model.contrato.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {


    List<Contrato> findContratoByServicioAndFechaIniIsGreaterThanEqualAndFechaFinIsLessThanEqual(ServicioGuia servicioGuia, LocalDate fechaIni, LocalDate fechaFin);
}
