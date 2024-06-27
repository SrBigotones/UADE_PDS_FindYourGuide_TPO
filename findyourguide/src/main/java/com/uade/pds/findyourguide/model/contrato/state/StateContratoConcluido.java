package com.uade.pds.findyourguide.model.contrato.state;

import com.uade.pds.findyourguide.enums.EstadoContrato;
import com.uade.pds.findyourguide.model.contrato.Contrato;

public class StateContratoConcluido implements IStateContrato{


    @Override
    public void cancelar(Contrato contrato) throws Exception{
        throw new Exception("No es posible cancelar un contrato concluido");
    }

    @Override
    public void aprobar(Contrato contrato) throws Exception{
        throw new Exception("No es posible aprobar un contrato concluido");
    }

    @Override
    public void reservar(Contrato contrato) throws Exception{
        throw new Exception("No es posible reservar un contrato concluido");
    }

    @Override
    public void concluir(Contrato contrato) throws Exception {
        throw new Exception("No es posible concluir un contrato ya concluido");
    }


}
