package com.uade.pds.findyourguide.service;

import com.uade.pds.findyourguide.model.Resenia;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.repository.ReseniaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReseniasService {

    @Autowired private ReseniaRepository reseniaRepository;

    public Optional<Resenia> obtenerResenia(long id){
        return this.reseniaRepository.findById(id);
    }

    public Resenia escribirResenia(Resenia resenia){
        return this.reseniaRepository.save(resenia);
    }

    public List<Resenia> obtenerReseniasDeGuia(long id_guia) {
        return reseniaRepository.findReseniasByServicioContratadoGuiaId(id_guia);
    }


    public List<Resenia> obtenerReseniasDeUsuario(Usuario usuario) {
        return reseniaRepository.findReseniasByUsuarioTurista(usuario);
    }

}
