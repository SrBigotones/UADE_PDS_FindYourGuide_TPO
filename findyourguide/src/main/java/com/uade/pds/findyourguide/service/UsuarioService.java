package com.uade.pds.findyourguide.service;

import com.uade.pds.findyourguide.enums.EstadoUsuario;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.model.user.UsuarioGuia;
import com.uade.pds.findyourguide.repository.UsuarioRepository;
import com.uade.pds.findyourguide.service.registro.IMetodoRegistroEstrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    private IMetodoRegistroEstrategy estrategiaRegistro;

    public Usuario registrarUsuarioGuia(UsuarioGuia usuario) {
        Usuario userToSave = estrategiaRegistro.registrarUsuario(usuario);
        usuario.setEstado(EstadoUsuario.PENDIENTE_VALIDACION);

        return registrarUsuario(userToSave);
    }

    public Usuario registrarUsuarioTurista(Usuario usuario) {
        Usuario userToSave = estrategiaRegistro.registrarUsuario(usuario);
        usuario.setEstado(EstadoUsuario.ACTIVO);
        return registrarUsuario(userToSave);
    }

    private Usuario registrarUsuario(Usuario usuario) {
        Usuario userToSave = estrategiaRegistro.registrarUsuario(usuario);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioRepository.save(userToSave);
    }

    public void cambiarEstrategia(IMetodoRegistroEstrategy estrategiaRegistro) {
        this.estrategiaRegistro = estrategiaRegistro;
    }

    public Optional<Usuario> findUserByEmail(String email){
        return usuarioRepository.findUserByEmail(email);
    }

    public Optional<Usuario> findUserById(long id){
        return usuarioRepository.findById(id);
    }

}
