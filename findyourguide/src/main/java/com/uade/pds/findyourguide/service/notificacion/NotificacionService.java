package com.uade.pds.findyourguide.service.notificacion;


import com.uade.pds.findyourguide.model.Notificacion;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificacionService {

    @Autowired private NotificacionRepository notificacionRepository;
    private PushNotificacionFirebase pushNotificacionFirebase;

    NotificacionService(){
        this.pushNotificacionFirebase = new PushNotificacionFirebase();
    }


    public void enviarNotificacion(Usuario usuario, String mensaje){
        Notificacion notificacion =new Notificacion();
        notificacion.setMensaje(mensaje);
        notificacion.setUsuario(usuario);
        notificacion.setTimestamp(LocalDateTime.now());
        notificacionRepository.save(notificacion);
        this.pushNotificacionFirebase.enviarNotificacion(notificacion);
    }
}
