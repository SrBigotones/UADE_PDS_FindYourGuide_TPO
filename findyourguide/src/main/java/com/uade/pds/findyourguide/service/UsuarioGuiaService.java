package com.uade.pds.findyourguide.service;

import com.uade.pds.findyourguide.controller.dto.ServicioGuiaDTO;
import com.uade.pds.findyourguide.enums.EstadoUsuario;
import com.uade.pds.findyourguide.enums.Idioma;
import com.uade.pds.findyourguide.enums.TipoServicio;
import com.uade.pds.findyourguide.model.CiudadPais;
import com.uade.pds.findyourguide.model.ServicioGuia;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.model.user.UsuarioGuia;
import com.uade.pds.findyourguide.repository.CiudadPaisRepository;
import com.uade.pds.findyourguide.repository.ServicioGuiaRepository;
import com.uade.pds.findyourguide.repository.UsuarioGuiaRepository;
import com.uade.pds.findyourguide.repository.specifications.UsuarioGuiaSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioGuiaService {

    @Autowired private UsuarioGuiaRepository usuarioGuiaRepository;
    @Autowired private ServicioGuiaRepository servicioGuiaRepository;
    @Autowired private CiudadPaisRepository ciudadPaisRepository;

    public Optional<UsuarioGuia> buscarUsuarioGuia(long id){
        return usuarioGuiaRepository.findById(id);
    }
    public List<UsuarioGuia> buscarTodosLosGuias(){return usuarioGuiaRepository.findAll();}

    public List<UsuarioGuia> buscarGuiasFiltradas(String pais, String ciudad, String nombre, String apellido, List<TipoServicio>servicios, List<Idioma> idiomas,  Integer puntuacion){

        List<Specification<UsuarioGuia>> specs = new ArrayList<>();
        if (nombre != null) specs.add(UsuarioGuiaSpecifications.hasNombre(nombre));
        if (apellido != null) specs.add(UsuarioGuiaSpecifications.hasApellido(apellido));
        if (ciudad != null) specs.add(UsuarioGuiaSpecifications.hasCiudad(ciudad));
        if (pais != null) specs.add(UsuarioGuiaSpecifications.hasPais(pais));
        if (idiomas != null && !idiomas.isEmpty()) specs.add(UsuarioGuiaSpecifications.hasIdiomas(idiomas));
        if (servicios != null && !servicios.isEmpty()) specs.add(UsuarioGuiaSpecifications.hasTipoServicio(servicios));
        if (puntuacion != null) specs.add(UsuarioGuiaSpecifications.hasPuntuacion(puntuacion));

        Specification<UsuarioGuia> combinedSpec = specs.stream().reduce(Specification::and).orElse(null);

        return usuarioGuiaRepository.findAll(combinedSpec);
    }

    public Optional<ServicioGuia> obtenerServicioPorId(long id){
        return servicioGuiaRepository.findById(id);
    }

    public void publicarServicio(ServicioGuia servicioGuia){
        servicioGuiaRepository.save(servicioGuia);
    }

    public List<UsuarioGuia> buscarUsuariosPendienteValidacion() {
        List<UsuarioGuia> usuarios = usuarioGuiaRepository.findAll();
        return usuarios.stream()
                .filter((u) -> EstadoUsuario.PENDIENTE_VALIDACION.equals(u.getEstado()) && u.getImgCredencial() != null)
                .collect(Collectors.toList());
    }

    public void saveAll(List<UsuarioGuia> usuarios) {
        usuarioGuiaRepository.saveAll(usuarios);
    }

    public void registrarImgCredencial(Usuario usuario, String imgUrl) {
        UsuarioGuia usuarioGuia = usuarioGuiaRepository.findById(usuario.getId()).get();
        usuarioGuia.setImgCredencial(imgUrl);

        usuarioGuiaRepository.save(usuarioGuia);
    }

}
