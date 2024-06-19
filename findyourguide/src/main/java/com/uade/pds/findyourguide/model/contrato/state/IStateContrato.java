package com.uade.pds.findyourguide.model.contrato.state;

import com.uade.pds.findyourguide.model.contrato.Contrato;

public interface IStateContrato {

    void pagar(Contrato contrato);
    void cancelar(Contrato contrato);
    void aprobar(Contrato contrato);
    void realizarReserva(Contrato contrato);
}
