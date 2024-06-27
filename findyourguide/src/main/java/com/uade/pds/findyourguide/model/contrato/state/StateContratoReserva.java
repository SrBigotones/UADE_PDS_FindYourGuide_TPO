package com.uade.pds.findyourguide.model.contrato.state;

import com.uade.pds.findyourguide.enums.EstadoContrato;
import com.uade.pds.findyourguide.model.Chat;
import com.uade.pds.findyourguide.model.contrato.Contrato;

import java.util.UUID;

public class StateContratoReserva implements IStateContrato{

    @Override
    public void cancelar(Contrato contrato) {
        contrato.setEstadoContrato(EstadoContrato.CANCELADO);
        contrato.cambiarEstado(new StateContratoCancelado());
    }

    @Override
    public void aprobar(Contrato contrato) {
        contrato.setEstadoContrato(EstadoContrato.ACEPTADO);
        Chat chat = new Chat();
        //MOCK
        chat.setCanalSendBird(UUID.randomUUID().toString());
        contrato.setChat(chat);
        contrato.cambiarEstado(new StateContratoAceptado());
    }

    @Override
    public void reservar(Contrato contrato) throws Exception {
        throw new Exception("No es posible reservar un contrato ya reservado");
    }

    @Override
    public void concluir(Contrato contrato) throws Exception {
        throw new Exception("No es posible concluir un contrato que se encuentra reservado");
    }


}
