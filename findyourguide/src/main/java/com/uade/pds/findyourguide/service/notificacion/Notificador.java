package com.uade.pds.findyourguide.service.notificacion;

import com.uade.pds.findyourguide.model.Notificacion;
import com.uade.pds.findyourguide.model.user.Usuario;

public interface Notificador {

     void enviarNotificacion(Notificacion notificacion);
}
