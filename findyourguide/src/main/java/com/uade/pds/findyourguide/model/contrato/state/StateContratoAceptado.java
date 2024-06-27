package com.uade.pds.findyourguide.model.contrato.state;

import com.uade.pds.findyourguide.enums.EstadoContrato;
import com.uade.pds.findyourguide.model.contrato.Contrato;

import java.util.concurrent.ExecutionException;

public class StateContratoAceptado implements IStateContrato{

    @Override
    public void cancelar(Contrato contrato) {
        contrato.setEstadoContrato(EstadoContrato.CANCELADO);
        contrato.cambiarEstado(new StateContratoCancelado());
    }

    @Override
    public void aprobar(Contrato contrato) throws Exception{
        throw new Exception("No es posible aprobar un contrato aprobado");
    }

    @Override
    public void reservar(Contrato contrato) throws Exception{
        throw new Exception("No es posible reservar un contrato aprobado");
    }

    @Override
    public void concluir(Contrato contrato) throws Exception {
        contrato.setEstadoContrato(EstadoContrato.CONCLUIDO);
        contrato.cambiarEstado(new StateContratoConcluido());
    }

}
