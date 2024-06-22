package com.uade.pds.findyourguide.service;

import com.uade.pds.findyourguide.controller.dto.GuiaDTO;
import com.uade.pds.findyourguide.model.ServicioGuia;
import com.uade.pds.findyourguide.model.user.UsuarioGuia;
import com.uade.pds.findyourguide.repository.ServicioGuiaRepository;
import com.uade.pds.findyourguide.repository.UsuarioGuiaRepository;
import com.uade.pds.findyourguide.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioGuiaService {



    @Autowired private UsuarioGuiaRepository usuarioGuiaRepository;
    @Autowired private ServicioGuiaRepository servicioGuiaRepository;



    public Optional<UsuarioGuia> buscarUsuarioGuia(long id){

        Optional<UsuarioGuia> usu = usuarioGuiaRepository.findById(id);
        return usu;
    }

    public void publicarServicio(ServicioGuia servicioGuia){
        servicioGuiaRepository.save(servicioGuia);
    }


}
