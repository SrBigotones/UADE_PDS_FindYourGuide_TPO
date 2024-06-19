package com.uade.pds.findyourguide.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uade.pds.findyourguide.controller.dto.UserDTO;
import com.uade.pds.findyourguide.model.User;
import com.uade.pds.findyourguide.security.JwtTokenUtil;
import com.uade.pds.findyourguide.service.UserService;
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

import javax.swing.text.html.parser.Entity;

@Controller
public class UserController {

    @Autowired private UserService userService;

    @Autowired private AuthenticationManager authenticationManager;

    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired private JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(userDTO.getEmail(), userDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        String jwtToken = jwtTokenUtil.generateToken(userDTO.getEmail());

        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Void> register(@RequestBody UserDTO userDTO) {
        User newUser = this.userDtoToUser(userDTO);

        User user = userService.saveUser(newUser);
        if (user == null) {
            throw new UsernameNotFoundException("Ocurrio un error al registrar y loguear");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(newUser.getEmail(), userDTO.getPassword()));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping(value = "/aver")
    public ResponseEntity<UserDTO> aver(@RequestBody UserDTO userDTO) {
        User user = this.userDtoToUser(userDTO);
        UserDTO mapper = this.userToUserDTO(user);
        System.out.println(mapper);
        return ResponseEntity.ok(mapper);
    }


    private User userDtoToUser(UserDTO userDTO){
        User user = User.builder()
                .email(userDTO.getEmail())
                .dni(userDTO.getDni())
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .sex(userDTO.getSex())
                .phoneNumber(userDTO.getPhoneNumber())
                .perfilImg(userDTO.getPerfilImg())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();

        return user;
    }

    private UserDTO userToUserDTO(User user){
        ObjectMapper objectMapper = new ObjectMapper();
        UserDTO userDTO =  objectMapper.convertValue(user, UserDTO.class);

        return userDTO;
    }

}
