package com.uade.pds.findyourguide.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uade.pds.findyourguide.controller.dto.GuiaDTO;
import com.uade.pds.findyourguide.controller.dto.ServicioGuiaDTO;
import com.uade.pds.findyourguide.model.ServicioGuia;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.model.user.UsuarioGuia;
import com.uade.pds.findyourguide.service.UsuarioGuiaService;
import com.uade.pds.findyourguide.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/guia")
public class UsuarioGuiaController {

    @Autowired
    private UsuarioGuiaService usuarioGuiaService;

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping("/buscar/{id}")
    public ResponseEntity<GuiaDTO> buscarGuia(@PathVariable long id){
        UsuarioGuia usuarioGuia= usuarioGuiaService.buscarUsuarioGuia(id).orElse(null);

        if(usuarioGuia == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(guiaToDTO(usuarioGuia));
    }
    @GetMapping("/buscarAll")
    public ResponseEntity<List<GuiaDTO>> buscarGuia(GuiaDTO guiaDTO){
        return null;
    }

    @PutMapping("/actualizar")
    public void actualizar(GuiaDTO guiaDTO){}


    @PostMapping("/servicio")
    public ResponseEntity publicarServicio(@RequestBody ServicioGuiaDTO servicioGuiaDTO, Authentication authentication){
//        var obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Optional<Usuario> usu = usuarioService.findUserByEmail(principal.getName());


        usuarioGuiaService.publicarServicio(this.servicioDTOToServicioGuia(servicioGuiaDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private GuiaDTO guiaToDTO(UsuarioGuia usuarioGuia){
        GuiaDTO guiaDTO = new GuiaDTO();

        guiaDTO.setId(usuarioGuia.getId());
        guiaDTO.setApellido(usuarioGuia.getApellido());
        guiaDTO.setNombre(usuarioGuia.getNombre());

        guiaDTO.setServicios(usuarioGuia.getListServicios().stream().map(this::servicioToDTO).collect(Collectors.toList()));

//        guiaDTO.setPuntuacion();
//        guiaDTO.setIdiomas();
//        guiaDTO.setUbicaciones();

        return guiaDTO;
    }


    private ServicioGuiaDTO servicioToDTO(ServicioGuia servicioGuia){
        ServicioGuiaDTO  servicioGuiaDTO = new ServicioGuiaDTO();

        servicioGuiaDTO.setNombre(servicioGuia.getNombre());
        servicioGuiaDTO.setPrecio(servicioGuia.getPrecio());
        servicioGuiaDTO.setTipoServicio(servicioGuia.getTipoServicio());
        servicioGuiaDTO.setDescripcion(servicioGuia.getDescripcion());
        servicioGuiaDTO.setGuiaId(servicioGuia.getGuia_id());

        return servicioGuiaDTO;
    }

    private ServicioGuia servicioDTOToServicioGuia(ServicioGuiaDTO servicioGuiaDTO){
        ServicioGuia servicioGuia = new ServicioGuia();

        servicioGuia.setNombre(servicioGuiaDTO.getNombre());
        servicioGuia.setPrecio(servicioGuiaDTO.getPrecio());
        servicioGuia.setTipoServicio(servicioGuiaDTO.getTipoServicio());
        servicioGuia.setDescripcion(servicioGuiaDTO.getDescripcion());
        servicioGuia.setGuia_id(servicioGuiaDTO.getGuiaId());

        return servicioGuia;
    }



}
