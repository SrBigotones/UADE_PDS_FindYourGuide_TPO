package com.uade.pds.findyourguide.service;

import com.uade.pds.findyourguide.model.Resenia;
import com.uade.pds.findyourguide.repository.ReseniaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReseniasService {

    @Autowired private ReseniaRepository reseniaRepository;

    public Optional<Resenia> obtenerResenia(long id){
        return this.reseniaRepository.findById(id);
    }

    public void escribirResenia(Resenia resenia){
        this.reseniaRepository.save(resenia);
    }

}
