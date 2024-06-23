package com.uade.pds.findyourguide.model.contrato.state;

import com.uade.pds.findyourguide.enums.EstadoContrato;

public class StateContratoFactory {

    static public IStateContrato crearState(EstadoContrato estadoContrato){
        IStateContrato stateContrato = null;
        switch (estadoContrato){
            case ACEPTADO -> stateContrato = new StateContratoAceptado();
            case RESERVA -> stateContrato = new StateContratoReserva();
            case CANCELADO -> stateContrato = new StateContratoCancelado();
            case CONCLUIDO -> stateContrato = new StateContratoConcluido();
        }

        return stateContrato;
    }
}
