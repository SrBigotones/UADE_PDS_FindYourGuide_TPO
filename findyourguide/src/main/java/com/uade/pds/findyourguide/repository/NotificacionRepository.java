package com.uade.pds.findyourguide.repository;

import com.uade.pds.findyourguide.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
}
