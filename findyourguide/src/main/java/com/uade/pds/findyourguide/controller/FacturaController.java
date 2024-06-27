package com.uade.pds.findyourguide.controller;

import com.uade.pds.findyourguide.controller.dto.FacturaDTO;
import com.uade.pds.findyourguide.model.Factura;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.security.CustomUserDetails;
import com.uade.pds.findyourguide.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/factura")
public class FacturaController {

    @Autowired private FacturaService facturaService;
    @GetMapping("/{idFactura}")
    public ResponseEntity obtenerFactura(@PathVariable long idFactura, Authentication authentication) throws Exception {

        Usuario usuario = ((CustomUserDetails) authentication.getPrincipal()).getUsuario();
        var factura = facturaService.obtenerFacturaPorId(idFactura);
        this.validarPermisoFactura(factura, usuario);
        return ResponseEntity.ok(this.FacturaToDTO(factura));
    }
    @GetMapping()
    public ResponseEntity obtenerTodasFacturas(Authentication authentication) throws Exception {

        Usuario usuario = ((CustomUserDetails) authentication.getPrincipal()).getUsuario();

        var listFacturas = facturaService.obtenerFacturasPorUsuario(usuario)
                .stream().map(this::FacturaToDTO).toList();

        return ResponseEntity.ok(listFacturas);
    }

    @PostMapping("/{idFactura}/abonar")
    public ResponseEntity abonarFactura(@PathVariable long idFactura , Authentication authentication) throws Exception {
        Usuario usuario = ((CustomUserDetails) authentication.getPrincipal()).getUsuario();
        var factura = facturaService.obtenerFacturaPorId(idFactura);
        this.validarPermisoFactura(factura, usuario);

        var saved = facturaService.abonar(factura);

        return ResponseEntity.ok(this.FacturaToDTO(saved));
    }



    private FacturaDTO FacturaToDTO(Factura factura){
        FacturaDTO facturaDTO = FacturaDTO.builder()
                .id(factura.getId())
                .estadoFactura(factura.getEstadoFactura())
                .creado(factura.getCreado())
                .pagado(factura.getPagado())
                .importe(factura.getImporte())
                .proposito(factura.getProposito())
                .idUsuario(factura.getUsuario().getId())
                .nombreServicio(factura.getContrato().getServicio().getNombre())
                .build();


        return facturaDTO;
    }


    private void validarPermisoFactura(Factura factura, Usuario usuario) throws Exception {
        if(factura.getUsuario().getId() != usuario.getId()){
            throw new Exception("Permiso no valido");
        }
    }
}
