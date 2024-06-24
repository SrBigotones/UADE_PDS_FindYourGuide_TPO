package com.uade.pds.findyourguide.service;

import com.uade.pds.findyourguide.enums.EstadoUsuario;
import com.uade.pds.findyourguide.model.ServicioGuia;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.model.user.UsuarioGuia;
import com.uade.pds.findyourguide.repository.ServicioGuiaRepository;
import com.uade.pds.findyourguide.repository.UsuarioGuiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioGuiaService {

    @Autowired private UsuarioGuiaRepository usuarioGuiaRepository;
    @Autowired private ServicioGuiaRepository servicioGuiaRepository;

    public Optional<UsuarioGuia> buscarUsuarioGuia(long id){
        return usuarioGuiaRepository.findById(id);
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
