package com.uade.pds.findyourguide.service;

import com.uade.pds.findyourguide.enums.EstadoContrato;
import com.uade.pds.findyourguide.enums.EstadoFactura;
import com.uade.pds.findyourguide.enums.MetodoNotificacion;
import com.uade.pds.findyourguide.model.ServicioGuia;
import com.uade.pds.findyourguide.model.contrato.Contrato;
import com.uade.pds.findyourguide.model.contrato.state.StateContratoAceptado;
import com.uade.pds.findyourguide.model.contrato.state.StateContratoCancelado;
import com.uade.pds.findyourguide.model.contrato.state.StateContratoReserva;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.model.user.UsuarioGuia;
import com.uade.pds.findyourguide.repository.ContratoRepository;
import com.uade.pds.findyourguide.repository.ServicioGuiaRepository;
import com.uade.pds.findyourguide.repository.UsuarioGuiaRepository;
import com.uade.pds.findyourguide.repository.UsuarioRepository;
import com.uade.pds.findyourguide.service.notificacion.NotificacionService;
import org.hibernate.Session;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContratoService {
    @Autowired private ContratoRepository contratoRepository;
    @Autowired private UsuarioGuiaService usuarioGuiaService;
    @Autowired private UsuarioService usuarioService;
    @Autowired private NotificacionService notificacionService;
    @Autowired private FacturaService facturaService;


    private static final int PORCENTAJE_RESERVA = 40;

    public Contrato contratar(Contrato contrato) throws Exception{


        ServicioGuia servicioGuia = usuarioGuiaService.obtenerServicioPorId(contrato.getServicio().getId()).get();
        Usuario usuario = usuarioService.findUserById(contrato.getUsuarioContratante().getId()).get();
        UsuarioGuia usuarioGuia = usuarioGuiaService.buscarUsuarioGuia(contrato.getUsuarioContratado().getId()).get();

        contrato.setUsuarioContratante(usuario);
        contrato.setUsuarioContratado(usuarioGuia);
        contrato.setServicio(servicioGuia);

        notificacionService.enviarNotificacion(usuarioGuia, "Se realizo una nueva reserva para el servicio " + contrato.getServicio().getNombre(), MetodoNotificacion.PUSH);

        if(this.checkDisponibilidad(contrato)){
            Contrato saved = contratoRepository.save(contrato);

            return saved;
        }else{
            throw new Exception("Cupos excedido");
        }


    }

    public Contrato concluirContrato(Contrato contrato) throws Exception {
        contrato.getStateContrato().concluir(contrato);
        facturaService.generarFactura(contrato, "Se concluye el contrato con exito", contrato.getServicio().getPrecio());

        return contratoRepository.save(contrato);
    }
    public Contrato confirmarContrato(Contrato contrato) throws Exception {
        contrato.getStateContrato().aprobar(contrato);

        double importe = contrato.getServicio().getPrecio() * PORCENTAJE_RESERVA;
        importe /= 100;
        facturaService.generarFactura(contrato, "Confirmada la reserva", importe);


        notificacionService.enviarNotificacion(contrato.getUsuarioContratante(), "Se confirmo el contrato para el servicio " + contrato.getServicio().getNombre(), MetodoNotificacion.PUSH);
        return contratoRepository.save(contrato);
    }

    public Contrato cancelarContratoPorGuia(Contrato contrato) throws Exception {
        contrato.getStateContrato().cancelar(contrato);

        var facturaReserva = facturaService.obtenerFacturasPorUsuario(contrato.getUsuarioContratante())
                .stream().filter(factura -> factura.getContrato() == contrato).toList().getFirst();
        
        
        if(facturaReserva.getEstadoFactura() == EstadoFactura.ABONADO){
            var factDevolucion =facturaService.generarFactura(contrato, "Se devuelve el pago por su reserva",-(facturaReserva.getImporte()));
            facturaService.abonar(factDevolucion);
        } else if (facturaReserva.getEstadoFactura() == EstadoFactura.PENDIENTE) {
            facturaService.revocar(facturaReserva);
        }

        notificacionService.enviarNotificacion(contrato.getUsuarioContratante(), "Se cancelo su reserva para el servicio " + contrato.getServicio().getNombre(), MetodoNotificacion.PUSH);
        return contratoRepository.save(contrato);
    }

    public Contrato cancelarContratoPorTurista(Contrato contrato) throws Exception {

        var facturaReserva = facturaService.obtenerFacturasPorUsuario(contrato.getUsuarioContratante())
                .stream().filter(factura -> factura.getContrato() == contrato).toList().getFirst();

        EstadoContrato estadoContrato = contrato.getEstadoContrato();
        contrato.getStateContrato().cancelar(contrato);
        if(estadoContrato == EstadoContrato.ACEPTADO && contrato.getFechaIni().isBefore(LocalDate.now())){
            //Se le cobra la totalidad del servicio al turista
            facturaService.generarFactura(contrato, "Se cancela un contrato ya aceptado y durante la fecha del viaje, se procede a cobrar la totalidad del servicio", contrato.getServicio().getPrecio() - facturaReserva.getImporte());
        }
        
        notificacionService.enviarNotificacion(contrato.getUsuarioContratante(), "Se cancelo su reserva para el servicio " + contrato.getServicio().getNombre(), MetodoNotificacion.PUSH);
        return contratoRepository.save(contrato);
    }





    public Optional<Contrato> obtenerContratoPorId(long id){
        return contratoRepository.findById(id);
    }

    public List<Contrato> obtenerContratosConFechaFin(LocalDate fechaFin) {
        return contratoRepository.findContratosByFechaFinAndEstadoContrato(fechaFin, EstadoContrato.CONCLUIDO);
    }


    public List<Contrato> obtenerContratoPorServicioYGuia(ServicioGuia servicioGuia,Usuario usuarioContratante) {
        return contratoRepository.findContratoesByUsuarioContratanteAndAndServicioAndEstadoContrato(usuarioContratante,servicioGuia,EstadoContrato.CONCLUIDO);
    }

    private Contrato findContrato(Contrato contrato){
        var contratoFound = contratoRepository.findById(contrato.getId());



        return contratoFound.orElseGet(null);
    }


    private boolean checkDisponibilidad(Contrato contrato){
        List<Contrato> contratoList = contratoRepository
                .findContratoByServicioAndFechaIniIsGreaterThanEqualAndFechaFinIsLessThanEqual(contrato.getServicio(), contrato.getFechaIni(), contrato.getFechaFin())
                .stream().filter(ctr -> ctr.getEstadoContrato() != EstadoContrato.CANCELADO && ctr.getEstadoContrato() != EstadoContrato.CONCLUIDO)
                .toList();


        int registrados = contratoList.size();

        if(registrados >= contrato.getServicio().getCupo()){
            return false;
        }

        return true;
    }


}
