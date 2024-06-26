package com.uade.pds.findyourguide.model.trofeo;

import com.uade.pds.findyourguide.model.Resenia;
import com.uade.pds.findyourguide.observer.IObservable;
import com.uade.pds.findyourguide.service.TrofeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TrofeoResenia extends CondicionTrofeo{



    @Override
    public void serNotificado(IObservable observable) {
        Resenia resenia = (Resenia) observable;
        super.trofeoService.ganoTrofeosResenias(resenia.getUsuarioTurista());
    }
}
