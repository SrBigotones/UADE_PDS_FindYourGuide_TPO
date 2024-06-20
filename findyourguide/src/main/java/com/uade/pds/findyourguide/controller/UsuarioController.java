package com.uade.pds.findyourguide.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uade.pds.findyourguide.controller.dto.UsuarioDTO;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.security.JwtTokenUtil;
import com.uade.pds.findyourguide.service.UsuarioService;
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

    @PostMapping(value = "/register")
    public ResponseEntity<Void> register(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario newUsuario = this.userDtoToUser(usuarioDTO);

        Usuario usuario = usuarioService.saveUser(newUsuario);
        if (usuario == null) {
            throw new UsernameNotFoundException("Ocurrio un error al registrar y loguear");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(newUsuario.getEmail(), usuarioDTO.getPassword()));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    private Usuario userDtoToUser(UsuarioDTO usuarioDTO){
        Usuario usuario = Usuario.builder()
                .email(usuarioDTO.getEmail())
                .dni(usuarioDTO.getDni())
                .nombre(usuarioDTO.getNombre())
                .apellido(usuarioDTO.getApellido())
                .sexo(usuarioDTO.getSexo())
                .numTelefono(usuarioDTO.getNumTelefono())
                .imgPerfil(usuarioDTO.getImgPerfil())
                .password(passwordEncoder.encode(usuarioDTO.getPassword()))
                .build();

        return usuario;
    }

    private UsuarioDTO userToUserDTO(Usuario usuario){
        ObjectMapper objectMapper = new ObjectMapper();
        UsuarioDTO usuarioDTO =  objectMapper.convertValue(usuario, UsuarioDTO.class);

        return usuarioDTO;
    }

}
