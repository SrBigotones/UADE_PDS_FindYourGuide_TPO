package com.uade.pds.findyourguide.observer;

public interface IObservable {

    void agregar(IObserver observer);

    void eliminar(IObserver observer);
    void notificar();
}
