package com.uade.pds.findyourguide.model.contrato.state;

import com.uade.pds.findyourguide.enums.EstadoContrato;
import com.uade.pds.findyourguide.model.contrato.Contrato;

public interface IStateContrato {

    void pagar(Contrato contrato, double importe) throws Exception;
    void cancelar(Contrato contrato) throws Exception;
    void aprobar(Contrato contrato) throws Exception;
    void realizarReserva(Contrato contrato) throws Exception;

}
