package com.uade.pds.findyourguide.model.contrato.state;

import com.uade.pds.findyourguide.enums.EstadoContrato;
import com.uade.pds.findyourguide.model.contrato.Contrato;

import java.util.concurrent.ExecutionException;

public class StateContratoAceptado implements IStateContrato{
    @Override
    public void pagar(Contrato contrato, double importe) throws Exception{
        double saldoPendiente = contrato.getServicio().getPrecio() - contrato.getImporte();


        if(saldoPendiente < importe){
            throw new Exception("El importe es mayor a la deuda a saldar del contrato");
        }

        contrato.setImporte(contrato.getImporte() + importe);
    }

    @Override
    public void cancelar(Contrato contrato) {
        contrato.setEstadoContrato(EstadoContrato.CANCELADO);
        contrato.setStateContrato(new StateContratoCancelado());
    }

    @Override
    public void aprobar(Contrato contrato) throws Exception{
        throw new Exception("No es posible aprobar un contrato aprobado");
    }

    @Override
    public void realizarReserva(Contrato contrato) throws Exception{
        throw new Exception("No es posible reservar un contrato aprobado");
    }

}
