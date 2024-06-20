package com.uade.pds.findyourguide.model.user;

public class UsuarioFactory {
    public static Usuario crearUsuarioTurista() {
        return new Usuario();
    }

    public static UsuarioGuia crearUsuarioGuia() {
        return new UsuarioGuia();
    }
}
