package com.uade.pds.findyourguide.repository;

import com.uade.pds.findyourguide.enums.EstadoContrato;
import com.uade.pds.findyourguide.model.ServicioGuia;
import com.uade.pds.findyourguide.model.contrato.Contrato;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.model.user.UsuarioGuia;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {


    List<Contrato> findContratoByServicioAndFechaIniIsGreaterThanEqualAndFechaFinIsLessThanEqual(ServicioGuia servicioGuia, LocalDate fechaIni, LocalDate fechaFin);

    List<Contrato> findContratosByFechaFinAndEstadoContrato(LocalDate fechaFin, EstadoContrato estadoContrato);

    List<Contrato> findContratoesByUsuarioContratanteAndAndServicioAndEstadoContrato(Usuario usuarioContratante,ServicioGuia servicioGuia,EstadoContrato estadoContrato);

    List<Contrato> findContratosByUsuarioContratado(UsuarioGuia usuarioGuia);
}

