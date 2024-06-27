package com.uade.pds.findyourguide.service.notificacion;

import com.uade.pds.findyourguide.model.Notificacion;

public class EmailNotificador implements Notificador{
    @Override
    public void enviarNotificacion(Notificacion notificacion) {
        String s = String.format("Se envia email a %s, con el contenido: %s", notificacion.getUsuario().getEmail(), notificacion.getMensaje());
        System.out.println(s);
    }
}
