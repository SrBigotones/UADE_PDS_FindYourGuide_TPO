package com.uade.pds.findyourguide.service;

import com.uade.pds.findyourguide.enums.EstadoContrato;
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
import org.hibernate.Session;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContratoService {
    @Autowired private ContratoRepository contratoRepository;
    @Autowired private UsuarioGuiaService usuarioGuiaService;
    @Autowired private UsuarioService usuarioService;

    public Contrato contratar(Contrato contrato) throws Exception{


        ServicioGuia servicioGuia = usuarioGuiaService.obtenerServicioPorId(contrato.getServicio().getId()).get();
        Usuario usuario = usuarioService.findUserById(contrato.getUsuarioContratante().getId()).get();
        UsuarioGuia usuarioGuia = usuarioGuiaService.buscarUsuarioGuia(contrato.getUsuarioContratado().getId()).get();

        contrato.setUsuarioContratante(usuario);
        contrato.setUsuarioContratado(usuarioGuia);
        contrato.setServicio(servicioGuia);

        if(this.checkDisponibilidad(contrato)){
            Contrato saved = contratoRepository.save(contrato);

            return saved;
        }else{
            throw new Exception("Cupos excedido");
        }


    }

    public Contrato confirmarContrato(Contrato contrato) throws Exception {
        contrato.getStateContrato().aprobar(contrato);
        return contratoRepository.save(contrato);
    }

    public Contrato cancelarContrato(Contrato contrato) throws Exception {
        contrato.getStateContrato().cancelar(contrato);
        return contratoRepository.save(contrato);
    }

    public Contrato pagarContrato(Contrato contrato, double importeAPagar) throws Exception{

        var stateContrato = contrato.getStateContrato();
        stateContrato.pagar(contrato, importeAPagar);

        return contratoRepository.save(contrato);
    }


    public Optional<Contrato> obtenerContratoPorId(long id){
        return contratoRepository.findById(id);
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
