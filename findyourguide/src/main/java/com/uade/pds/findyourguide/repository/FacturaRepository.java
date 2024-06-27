package com.uade.pds.findyourguide.repository;

import com.uade.pds.findyourguide.model.Factura;
import com.uade.pds.findyourguide.model.user.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    public List<Factura> findAllByUsuario(Usuario usuario);
}
