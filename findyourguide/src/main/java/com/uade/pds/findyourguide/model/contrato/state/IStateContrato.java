package com.uade.pds.findyourguide.model.contrato.state;

import com.uade.pds.findyourguide.enums.EstadoContrato;
import com.uade.pds.findyourguide.model.contrato.Contrato;

public interface IStateContrato {

    void cancelar(Contrato contrato) throws Exception;
    void aprobar(Contrato contrato) throws Exception;
    void reservar(Contrato contrato) throws Exception;

    void concluir(Contrato contrato) throws Exception;


}
