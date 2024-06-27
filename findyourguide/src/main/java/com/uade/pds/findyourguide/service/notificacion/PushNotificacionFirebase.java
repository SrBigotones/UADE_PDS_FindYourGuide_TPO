package com.uade.pds.findyourguide.service.notificacion;

import com.uade.pds.findyourguide.model.Notificacion;

public class PushNotificacionFirebase implements Notificador{


    public void enviarNotificacion(Notificacion notificacion){

        System.out.println("Se notifica por medio de Firebase");
    }
}
