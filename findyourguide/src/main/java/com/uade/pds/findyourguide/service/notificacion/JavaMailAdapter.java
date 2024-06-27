package com.uade.pds.findyourguide.service.notificacion;

import com.uade.pds.findyourguide.enums.MetodoNotificacion;
import com.uade.pds.findyourguide.model.user.Usuario;

public class JavaMailAdapter implements AdapterNotificacionMail{
    @Override
    public void enviarNotificacion(Usuario usuario, String mensaje, MetodoNotificacion metodoNotificacion) {
        System.out.println("Notificacion enviada por mail");
    }
}
