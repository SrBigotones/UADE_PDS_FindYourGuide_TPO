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
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<UsuarioGuia> buscarGuiasFiltradas(String ciudad, String pais, String nombre, String apellido, List<Idioma> idiomas, List<TipoServicio>servicios, int puntuacion){
        CiudadPais ciudadPais = ciudadPaisRepository.findById(1L).get();

        List<UsuarioGuia> usuarioGuiaList =  usuarioGuiaRepository.findByNombreAndApellidoAndIdiomasInAndListServiciosTipoServicioAndPuntuacionAndListaCiudadesActivo_CiudadAndListaCiudadesActivo_Pais(nombre, apellido, idiomas,servicios,puntuacion, ciudad, pais);
        /*List<UsuarioGuia> response = new ArrayList<>();
        for (UsuarioGuia usuarioGuia: usuarioGuiaList
             ) {
            if(usuarioGuia.getListaCiudadesActivo().contains(ciudad) || usuarioGuia.getListaCiudadesActivo().contains(pais)){
                response.add(usuarioGuia);
            }
        }*/
        return usuarioGuiaList;
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
