package com.uade.pds.findyourguide.controller;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<List<GuiaDTO>> buscarGuia(){
        List<GuiaDTO> dtos = usuarioGuiaService.buscarTodos().stream().map(this::guiaToDTO).collect(Collectors.toList());

//        List<GuiaDTO> dtos = usuarioGuiaService.buscarGuiasPorFiltro(this.dtoToUsuario(guiaDTO)).stream().map(this::guiaToDTO).toList();

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/buscarAll/filtro")
    public ResponseEntity<List<GuiaDTO>> buscarGuiaFiltro(@RequestBody GuiaDTO guiaDTO){
//        List<GuiaDTO> dtos = usuarioGuiaService.buscarTodos().stream().map(this::guiaToDTO).collect(Collectors.toList());

        List<GuiaDTO> dtos = usuarioGuiaService.buscarGuiasPorFiltro(this.dtoToUsuario(guiaDTO)).stream().map(this::guiaToDTO).toList();

        return ResponseEntity.ok(dtos);
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


    private UsuarioGuia dtoToUsuario(GuiaDTO dto){
        UsuarioGuia usuarioGuia = new UsuarioGuia();

        usuarioGuia.setIdiomas(dto.getIdiomas());
        usuarioGuia.setPuntuacion(dto.getPuntuacion());
        usuarioGuia.setNombre(dto.getNombre());
        usuarioGuia.setApellido(dto.getApellido());


        return usuarioGuia;

    }


    private ServicioGuiaDTO servicioToDTO(ServicioGuia servicioGuia){
        ServicioGuiaDTO  servicioGuiaDTO = new ServicioGuiaDTO();

        servicioGuiaDTO.setId(servicioGuia.getId());
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
