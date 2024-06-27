package com.uade.pds.findyourguide.repository;

import com.uade.pds.findyourguide.enums.Idioma;
import com.uade.pds.findyourguide.enums.TipoServicio;
import com.uade.pds.findyourguide.model.CiudadPais;
import com.uade.pds.findyourguide.model.ServicioGuia;
import com.uade.pds.findyourguide.model.user.UsuarioGuia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioGuiaRepository extends JpaRepository<UsuarioGuia, Long> {
    List<UsuarioGuia> findByNombreAndApellidoAndIdiomasInAndListServiciosTipoServicioAndPuntuacionAndListaCiudadesActivo_CiudadAndListaCiudadesActivo_Pais(String nombre,String apellido, List<Idioma> idiomas, List<TipoServicio> servicios, int puntuacion, String ciudad, String pais);
}
