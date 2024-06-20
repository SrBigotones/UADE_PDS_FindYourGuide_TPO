package com.uade.pds.findyourguide.service;

import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario saveUser(Usuario usuario) {
        // TODO check no esta

        return usuarioRepository.save(usuario);
    }


}
