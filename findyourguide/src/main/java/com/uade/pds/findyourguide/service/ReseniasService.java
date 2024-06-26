package com.uade.pds.findyourguide.service;

import com.uade.pds.findyourguide.model.Resenia;
import com.uade.pds.findyourguide.model.ServicioGuia;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.observer.IObservable;
import com.uade.pds.findyourguide.observer.IObserver;
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
        Resenia saved = this.reseniaRepository.save(resenia);
        saved.notificar();
        return saved;
    }

    public List<Resenia> obtenerReseniasDeGuia(long id_guia) {
        return reseniaRepository.findReseniasByServicioContratadoGuiaId(id_guia);
    }


    public List<Resenia> obtenerReseniasDeUsuario(Usuario usuario) {
        return reseniaRepository.findReseniasByUsuarioTurista(usuario);
    }


    public boolean yaHizoResenia(Usuario usuario, ServicioGuia servicioGuia) {
        return !reseniaRepository.findReseniasByUsuarioTuristaAndAndServicioContratado(usuario, servicioGuia).isEmpty();
    }

}
