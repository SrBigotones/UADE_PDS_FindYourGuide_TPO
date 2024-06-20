package com.uade.pds.findyourguide.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uade.pds.findyourguide.controller.dto.UsuarioDTO;
import com.uade.pds.findyourguide.enums.EstrategiaRegistro;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.model.user.UsuarioFactory;
import com.uade.pds.findyourguide.model.user.UsuarioGuia;
import com.uade.pds.findyourguide.security.JwtTokenUtil;
import com.uade.pds.findyourguide.service.UsuarioService;
import com.uade.pds.findyourguide.service.registro.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {

    @Autowired private UsuarioService usuarioService;

    @Autowired private AuthenticationManager authenticationManager;

    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired private JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody UsuarioDTO usuarioDTO) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(usuarioDTO.getEmail(), usuarioDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        String jwtToken = jwtTokenUtil.generateToken(usuarioDTO.getEmail());

        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping(value = "/registrar/turista")
    public ResponseEntity<Void> registrarTurista(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario newUsuario = UsuarioFactory.crearUsuarioTurista();
        mapUserDTOToUser(usuarioDTO, newUsuario);

        Usuario usuario = usuarioService.registrarUsuarioTurista(newUsuario);

        if (usuario == null) {
            throw new UsernameNotFoundException("Ocurrio un error al registrar y loguear");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(newUsuario.getEmail(), usuarioDTO.getPassword()));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping(value = "/registrar/guia")
    public ResponseEntity<Void> registrarGuia(@RequestBody UsuarioDTO usuarioDTO, @RequestParam("tipoRegistro") EstrategiaRegistro estrategiaRegistro) {
        UsuarioGuia newUsuario = UsuarioFactory.crearUsuarioGuia();

        mapUserDTOToUser(usuarioDTO, newUsuario);

        usuarioService.cambiarEstrategia(mapEstrategiaRegistro(estrategiaRegistro));
        Usuario usuario = usuarioService.registrarUsuarioGuia(newUsuario);



        if (usuario == null) {
            throw new UsernameNotFoundException("Ocurrio un error al registrar y loguear");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(newUsuario.getEmail(), usuarioDTO.getPassword()));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    private <T extends Usuario> T mapUserDTOToUser(UsuarioDTO usuarioDTO, T usuario){
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setDni(usuarioDTO.getDni());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setSexo(usuarioDTO.getSexo());
        usuario.setNumTelefono(usuarioDTO.getNumTelefono());
        usuario.setImgPerfil(usuarioDTO.getImgPerfil());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));

        return usuario;
    }

    private UsuarioDTO userToUserDTO(Usuario usuario){
        ObjectMapper objectMapper = new ObjectMapper();
        UsuarioDTO usuarioDTO =  objectMapper.convertValue(usuario, UsuarioDTO.class);

        return usuarioDTO;
    }

    private IMetodoRegistroEstrategy mapEstrategiaRegistro(EstrategiaRegistro estrategiaEnum) {
        return switch (estrategiaEnum) {
            case EMAIL_PASSWORD -> new RegistroEmailPasswordEstrategy();
            case APPLE -> new RegistroAppleEstrategy();
            case FACEBOOK -> new RegistroFacebookEstrategy();
            case GOOGLE -> new RegistroGoogleEstrategy();
            default -> null;
        };
    }
}
