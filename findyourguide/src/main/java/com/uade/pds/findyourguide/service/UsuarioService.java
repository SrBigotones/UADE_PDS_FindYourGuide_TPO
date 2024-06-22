package com.uade.pds.findyourguide.service;

import com.uade.pds.findyourguide.enums.EstrategiaRegistro;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.model.user.UsuarioGuia;
import com.uade.pds.findyourguide.repository.UsuarioRepository;
import com.uade.pds.findyourguide.service.registro.IMetodoRegistroEstrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    private IMetodoRegistroEstrategy estrategiaRegistro;

    public Usuario registrarUsuarioGuia(UsuarioGuia newUsuario) {

        return registrarUsuario(newUsuario);
    }

    public Usuario registrarUsuarioTurista(Usuario newUsuario) {

        return registrarUsuario(newUsuario);
    }

    private Usuario registrarUsuario(Usuario usuario) {
        Usuario userToSave = estrategiaRegistro.registrarUsuario(usuario);
        return usuarioRepository.save(userToSave);
    }

    public void cambiarEstrategia(IMetodoRegistroEstrategy estrategiaRegistro) {
        this.estrategiaRegistro = estrategiaRegistro;
    }


    public Optional<Usuario> findUserByEmail(String email){
        return usuarioRepository.findUserByEmail(email);
    }
}
