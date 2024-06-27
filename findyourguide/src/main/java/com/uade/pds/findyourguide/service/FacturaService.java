package com.uade.pds.findyourguide.service;

import com.uade.pds.findyourguide.enums.EstadoFactura;
import com.uade.pds.findyourguide.model.Factura;
import com.uade.pds.findyourguide.model.contrato.Contrato;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    @Autowired private FacturaRepository facturaRepository;

    public Factura generarFactura(Contrato contrato, String proposito, double importe){

        Factura factura = new Factura();
        factura.setImporte(importe);
        factura.setProposito(proposito);
        factura.setContrato(contrato);
        factura.setUsuario(contrato.getUsuarioContratante());
        factura.setCreado(LocalDateTime.now());
        factura.setEstadoFactura(EstadoFactura.PENDIENTE);

        return facturaRepository.save(factura);
    }

    public Factura abonar(Factura factura){
        factura.setEstadoFactura(EstadoFactura.ABONADO);

        return facturaRepository.save(factura);
    }

    public Factura revocar(Factura factura){
        factura.setEstadoFactura(EstadoFactura.REVOCADO);

        return facturaRepository.save(factura);
    }

    public List<Factura> obtenerFacturasPorUsuario(Usuario usuario){
        return facturaRepository.findAllByUsuario(usuario);
    }

    public Factura obtenerFacturaPorId(long id){
        return facturaRepository.findById(id).get();
    }




}
