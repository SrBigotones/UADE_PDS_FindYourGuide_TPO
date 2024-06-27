package com.uade.pds.findyourguide.service.notificacion;

import com.uade.pds.findyourguide.enums.MetodoNotificacion;
import com.uade.pds.findyourguide.model.user.Usuario;

public interface AdapterNotificacionMail {
    void enviarNotificacion(Usuario usuario, String mensaje, MetodoNotificacion metodoNotificacion);
}
