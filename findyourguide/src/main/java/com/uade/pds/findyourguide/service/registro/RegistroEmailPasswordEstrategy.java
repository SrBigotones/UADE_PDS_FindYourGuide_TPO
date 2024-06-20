package com.uade.pds.findyourguide.service.registro;

import com.uade.pds.findyourguide.model.user.Usuario;

public class RegistroEmailPasswordEstrategy implements IMetodoRegistroEstrategy {

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        return usuario;
    }
}
