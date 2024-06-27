package com.uade.pds.findyourguide.repository;

import com.uade.pds.findyourguide.model.user.UsuarioGuia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioGuiaRepository extends JpaRepository<UsuarioGuia, Long> {

}
