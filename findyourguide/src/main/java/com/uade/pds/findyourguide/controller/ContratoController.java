package com.uade.pds.findyourguide.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uade.pds.findyourguide.controller.dto.ContratoDTO;
import com.uade.pds.findyourguide.controller.dto.OperacionContratoDTO;
import com.uade.pds.findyourguide.controller.dto.ServicioGuiaDTO;
import com.uade.pds.findyourguide.controller.dto.UsuarioDTO;
import com.uade.pds.findyourguide.enums.EstadoContrato;
import com.uade.pds.findyourguide.model.ServicioGuia;
import com.uade.pds.findyourguide.model.contrato.Contrato;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.model.user.UsuarioGuia;
import com.uade.pds.findyourguide.security.CustomUserDetails;
import com.uade.pds.findyourguide.service.ContratoService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController // o @Controller
@RequestMapping("/contrato")
public class ContratoController {


    @Autowired private ContratoService contratoService;


    @PostMapping
    public ResponseEntity contratar(@RequestBody ContratoDTO contratoDTO, Authentication authentication){
        Usuario usuario = ((CustomUserDetails) authentication.getPrincipal()).getUsuario();
        Contrato contrato = this.dtoToContrato(contratoDTO);
        contrato.setUsuarioContratante(usuario);
        contrato.setEstadoContrato(EstadoContrato.CONCLUIDO);

        Contrato contratoSaved = null;
        try {
            contratoSaved = contratoService.contratar(contrato);
            ContratoDTO contratoDTO1 = this.contratoToDTO(contratoSaved);
            return ResponseEntity.ok(contratoDTO1);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PostMapping("/{contratoId}/abonar")
    public ResponseEntity abonarContrato(@PathVariable long contratoId, @RequestBody Double importe, Authentication authentication) throws Exception {
        Contrato contrato = contratoService.obtenerContratoPorId(contratoId).get();
        Usuario usuario = ((CustomUserDetails) authentication.getPrincipal()).getUsuario();

        this.verificarPermisoUsuarioContrante(contrato, usuario);

        Contrato saved = contratoService.pagarContrato(contrato, importe);
        ContratoDTO contratoDTO = this.contratoToDTO(saved);
        return new ResponseEntity<>(contratoDTO, HttpStatus.OK);
    }

    @PostMapping("/{contratoId}/cancelar")
    public ResponseEntity cancelarContrato(@PathVariable long contratoId, Authentication authentication) throws Exception {
        Contrato contrato = contratoService.obtenerContratoPorId(contratoId).get();

        Usuario usuario = ((CustomUserDetails) authentication.getPrincipal()).getUsuario();

        try {
            this.verificarPermisoUsuarioContrante(contrato, usuario);
        }catch (Exception e){
            this.verificarPermisoUsuarioContrado(contrato, usuario);
        }

        Contrato saved = contratoService.cancelarContrato(contrato);

        ContratoDTO contratoDTO = this.contratoToDTO(saved);
        return new ResponseEntity<>(contratoDTO, HttpStatus.OK);
    }

    @PostMapping("/{contratoId}/confirmar")
    public ResponseEntity confirmarContrato(@PathVariable long contratoId, Authentication authentication) throws Exception {
        Contrato contrato = contratoService.obtenerContratoPorId(contratoId).get();

        Usuario usuario = ((CustomUserDetails) authentication.getPrincipal()).getUsuario();

        this.verificarPermisoUsuarioContrado(contrato, usuario);

        Contrato saved = contratoService.confirmarContrato(contrato);

        ContratoDTO contratoDTO = this.contratoToDTO(saved);
        return new ResponseEntity<>(contratoDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity obtenerContratoPorId(@PathVariable long id){
        ContratoDTO contratoDTO = this.contratoToDTO(contratoService.obtenerContratoPorId(id).get());

        return ResponseEntity.ok(contratoDTO);
    }

    private void verificarPermisoUsuarioContrante(Contrato contrato, Usuario usuario) throws Exception{
        if(contrato.getUsuarioContratante().getId() != usuario.getId()){
            throw new Exception("El usuario no corresponde al contrato");
        }
    }
    private void verificarPermisoUsuarioContrado(Contrato contrato, Usuario usuario) throws Exception{
        if(contrato.getUsuarioContratado().getId() != usuario.getId()){
            throw new Exception("El usuario no corresponde al contrato");
        }
    }



    private Contrato dtoToContrato(ContratoDTO contratoDTO){

        Contrato contrato = new Contrato();
        contrato.setImporte(contratoDTO.getImporte());
        contrato.setFechaIni(LocalDate.parse(contratoDTO.getFechaIni()));
        contrato.setFechaFin(LocalDate.parse(contratoDTO.getFechaFin()));
        contrato.setUsuarioContratado(this.usuarioDtoToUsuarioGuia(contratoDTO.getUsuarioGuia()));
        contrato.setUsuarioContratante(this.usuarioDtoToUsuario(contratoDTO.getUsuarioContratante()));
        contrato.setServicio(this.servicioDTOToServicioGuia(contratoDTO.getServicio()));
        contrato.setEstadoContrato(EstadoContrato.RESERVA);

        return contrato;
    }


    private Usuario usuarioDtoToUsuario(UsuarioDTO usuarioDTO){
        if(usuarioDTO == null) return null;

        Usuario usuario = new Usuario();

        usuario.setId(usuarioDTO.getId());

        return usuario;
    }

    private UsuarioGuia usuarioDtoToUsuarioGuia(UsuarioDTO usuarioDTO){
        UsuarioGuia usuario = new UsuarioGuia();
        usuario.setId(usuarioDTO.getId());
        return usuario;
    }



    private ServicioGuia servicioDTOToServicioGuia(ServicioGuiaDTO servicioGuiaDTO){
        ServicioGuia servicioGuia = new ServicioGuia();

        servicioGuia.setId(servicioGuiaDTO.getId());
        servicioGuia.setNombre(servicioGuiaDTO.getNombre());
        servicioGuia.setPrecio(servicioGuiaDTO.getPrecio());
        servicioGuia.setTipoServicio(servicioGuiaDTO.getTipoServicio());
        servicioGuia.setDescripcion(servicioGuiaDTO.getDescripcion());
        servicioGuia.setGuia_id(servicioGuiaDTO.getGuiaId());

        return servicioGuia;
    }

    private ContratoDTO contratoToDTO(Contrato contrato){
        ContratoDTO contratoDTO = new ContratoDTO();
        contratoDTO.setIdContrato(contratoDTO.getIdContrato());
        contratoDTO.setFechaIni(contrato.getFechaIni().toString());
        contratoDTO.setFechaFin(contrato.getFechaFin().toString());
        contratoDTO.setEstado(contrato.getEstadoContrato());
        contratoDTO.setImporte(contrato.getImporte());

        contratoDTO.setUsuarioContratante(this.usuarioToDTO(contrato.getUsuarioContratante()));
        contratoDTO.setUsuarioGuia(this.usuarioToDTO(contrato.getUsuarioContratado()));
        contratoDTO.setServicio(this.servicioGuiaToDTO(contrato.getServicio()));

        return contratoDTO;
    }

    private UsuarioDTO usuarioToDTO(Usuario usuario){

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail(usuario.getEmail());

        return usuarioDTO;
    }

    private ServicioGuiaDTO servicioGuiaToDTO(ServicioGuia servicioGuia){
        ServicioGuiaDTO servicioGuiaDTO = new ServicioGuiaDTO();

        servicioGuiaDTO.setTipoServicio(servicioGuia.getTipoServicio());
        servicioGuiaDTO.setNombre(servicioGuia.getNombre());
        servicioGuiaDTO.setDescripcion(servicioGuia.getDescripcion());
        servicioGuiaDTO.setPrecio(servicioGuia.getPrecio());

        return servicioGuiaDTO;
    }

}
