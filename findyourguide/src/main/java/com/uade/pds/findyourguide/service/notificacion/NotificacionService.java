package com.uade.pds.findyourguide.service.notificacion;


import com.uade.pds.findyourguide.model.Notificacion;
import com.uade.pds.findyourguide.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    @Autowired private NotificacionRepository notificacionRepository;
    private PushNotificacionFirebase pushNotificacionFirebase;

    NotificacionService(){
        this.pushNotificacionFirebase = new PushNotificacionFirebase();
    }


    public void enviarNotificacion(Notificacion notificacion){
        this.pushNotificacionFirebase.enviarNotificacion(notificacion);
    }
}
