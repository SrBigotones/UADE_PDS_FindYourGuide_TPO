package com.uade.pds.findyourguide.service.registro;

import com.uade.pds.findyourguide.model.user.Usuario;

public class RegistroAppleEstrategy implements IMetodoRegistroEstrategy {

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        throw new RuntimeException("Not supported");
    }
}
