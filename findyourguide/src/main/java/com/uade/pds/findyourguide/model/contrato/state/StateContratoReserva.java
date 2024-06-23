package com.uade.pds.findyourguide.model.contrato.state;

import com.uade.pds.findyourguide.enums.EstadoContrato;
import com.uade.pds.findyourguide.model.contrato.Contrato;

public class StateContratoReserva implements IStateContrato{
    @Override
    public void pagar(Contrato contrato, double importe) {
        contrato.setImporte(contrato.getImporte() + importe);
    }

    @Override
    public void cancelar(Contrato contrato) {
        contrato.setEstadoContrato(EstadoContrato.CANCELADO);
        contrato.setStateContrato(new StateContratoCancelado());
    }

    @Override
    public void aprobar(Contrato contrato) {
        contrato.setEstadoContrato(EstadoContrato.ACEPTADO);
        contrato.setStateContrato(new StateContratoAceptado());
    }

    @Override
    public void realizarReserva(Contrato contrato) throws Exception {
        throw new Exception("No es posible reservar un contrato ya reservado");
    }

}
