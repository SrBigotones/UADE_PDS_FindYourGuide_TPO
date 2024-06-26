package com.uade.pds.findyourguide.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uade.pds.findyourguide.controller.dto.GuiaDTO;
import com.uade.pds.findyourguide.controller.dto.ServicioGuiaDTO;
import com.uade.pds.findyourguide.model.ServicioGuia;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.model.user.UsuarioGuia;
import com.uade.pds.findyourguide.security.CustomUserDetails;
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
import java.util.ArrayList;
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
        Optional<UsuarioGuia> usuarioGuia = usuarioGuiaService.buscarUsuarioGuia(id);

        if(usuarioGuia.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(guiaToDTO(usuarioGuia.get()));
    }
    @GetMapping("/buscarAll")
    public ResponseEntity<List<GuiaDTO>> buscarGuia(GuiaDTO guiaDTO){
        List<UsuarioGuia> listaUsuariosGuia = usuarioGuiaService.buscarTodosLosGuias();

        if (listaUsuariosGuia.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<GuiaDTO> listaGuiasDTO = new ArrayList<>();

        for (UsuarioGuia usuarioGuia : listaUsuariosGuia) {
            GuiaDTO usuarioGuiaDto = guiaToDTO(usuarioGuia);
            listaGuiasDTO.add(usuarioGuiaDto);
        }

        return ResponseEntity.ok(listaGuiasDTO);
    }

    @PutMapping("/actualizar")
    public void actualizar(GuiaDTO guiaDTO) {

    }

    @PostMapping("/servicio")
    public ResponseEntity<Void> publicarServicio(@RequestBody ServicioGuiaDTO servicioGuiaDTO, Authentication authentication) {
        Usuario usuario = ((CustomUserDetails) authentication.getPrincipal()).getUsuario();

        usuarioGuiaService.publicarServicio(this.mapDTOToServicioGuia(servicioGuiaDTO, usuario.getId()));
        return new ResponseEntity<>(HttpStatus.OK);
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

    private ServicioGuia mapDTOToServicioGuia(ServicioGuiaDTO servicioGuiaDTO, long idGuia){
        ServicioGuia servicioGuia = new ServicioGuia();

        servicioGuia.setNombre(servicioGuiaDTO.getNombre());
        servicioGuia.setPrecio(servicioGuiaDTO.getPrecio());
        servicioGuia.setTipoServicio(servicioGuiaDTO.getTipoServicio());
        servicioGuia.setDescripcion(servicioGuiaDTO.getDescripcion());
        servicioGuia.setGuia_id(idGuia);

        return servicioGuia;
    }



}
