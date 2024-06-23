package com.uade.pds.findyourguide.model.contrato.state;

import com.uade.pds.findyourguide.enums.EstadoContrato;

public class StateContratoFactory {

    static public IStateContrato crearState(EstadoContrato estadoContrato){
        IStateContrato stateContrato = null;
        switch (estadoContrato){
            case ACEPTADO -> stateContrato = new StateContratoAceptado();
            case RESERVA -> stateContrato = new StateContratoAceptado();
            case CANCELADO -> stateContrato = new StateContratoAceptado();
            case CONCLUIDO -> stateContrato = new StateContratoAceptado();
        }

        return stateContrato;
    }
}
