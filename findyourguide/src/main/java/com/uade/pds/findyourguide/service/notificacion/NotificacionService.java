package com.uade.pds.findyourguide.service.notificacion;


import com.uade.pds.findyourguide.enums.MetodoNotificacion;
import com.uade.pds.findyourguide.model.Notificacion;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificacionService{

    @Autowired private NotificacionRepository notificacionRepository;
    private Notificador notificador;


    public void enviarNotificacion(Usuario usuario, String mensaje, MetodoNotificacion metodoNotificacion){

        switch (metodoNotificacion){
            case PUSH -> this.cambiarEstrategiaNotificador(new PushNotificacionFirebase());
            case EMAIL -> this.cambiarEstrategiaNotificador(new EmailNotificador());
        }

        Notificacion notificacion =new Notificacion();
        notificacion.setMensaje(mensaje);
        notificacion.setUsuario(usuario);
        notificacion.setTimestamp(LocalDateTime.now());
        notificacionRepository.save(notificacion);
        this.notificador.enviarNotificacion(notificacion);
    }


    private void cambiarEstrategiaNotificador(Notificador notificador){
        this.notificador = notificador;
    }
}
