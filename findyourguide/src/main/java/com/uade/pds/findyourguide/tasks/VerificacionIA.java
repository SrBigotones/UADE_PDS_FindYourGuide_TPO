package com.uade.pds.findyourguide.tasks;

import com.uade.pds.findyourguide.model.user.UsuarioGuia;

public class VerificacionIA implements IVerificacionGuiaAdapter {
    @Override
    public boolean verificar(UsuarioGuia usuario) {

        // Se verifica con librería externa
        return true;
    }
}
