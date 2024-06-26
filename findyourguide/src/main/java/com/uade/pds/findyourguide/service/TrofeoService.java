package com.uade.pds.findyourguide.service;


import com.uade.pds.findyourguide.controller.dto.TrofeoDTO;
import com.uade.pds.findyourguide.model.Notificacion;
import com.uade.pds.findyourguide.model.Resenia;
import com.uade.pds.findyourguide.model.trofeo.TipoTrofeo;
import com.uade.pds.findyourguide.model.trofeo.Trofeo;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.observer.IObservable;
import com.uade.pds.findyourguide.observer.IObserver;
import com.uade.pds.findyourguide.repository.TipoTrofeoRepository;
import com.uade.pds.findyourguide.repository.TrofeoRepository;
import com.uade.pds.findyourguide.service.notificacion.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
public class TrofeoService{



    @Autowired private TrofeoRepository trofeoRepository;
    @Autowired private TipoTrofeoRepository tipoTrofeoRepository;
    @Autowired private ReseniasService reseniasService;
    @Autowired private NotificacionService notificacionService;




    public Trofeo otorgarTrofeo(Usuario usuarioGanador, String nombreTrofeo) {
        Optional<TipoTrofeo> tipo_trofeo = tipoTrofeoRepository.findByNombreTrofeo(nombreTrofeo);
        if (tipo_trofeo.isEmpty()){
            return null;
        }
        Trofeo trofeo = new Trofeo();
        trofeo.setTrofeoOtorgado(tipo_trofeo.get());
        trofeo.setUsuarioGanador(usuarioGanador);

        return trofeoRepository.save(trofeo);
    }


    public List<Trofeo> buscarTrofeos(Usuario usuarioBuscado) {
        return trofeoRepository.findTrofeosByUsuarioGanador(usuarioBuscado);
    }



    public Trofeo ganoTrofeosExito(Usuario usuario) {
        // Gana trofeo al exito por calificacion mayor a 4.5 en minimo 10 resenias
        List<Resenia> listaResenias = reseniasService.obtenerReseniasDeGuia(usuario.getId());
        System.out.println(listaResenias.size());
        Trofeo trofeoOtorgado = new Trofeo();
        Optional<TipoTrofeo> trofeo = tipoTrofeoRepository.findByNombreTrofeo("Trofeo al Exito");
        //Ya gano el trofeo

        boolean ganoElTrofeo = trofeoRepository.findByTrofeoOtorgadoAndUsuarioGanador(trofeo.get(),usuario).isPresent();

        if (!ganoElTrofeo){
        if (listaResenias.size() >= 1) {
            OptionalDouble promedioResenias = listaResenias.stream().mapToDouble(resenia -> resenia.getCalificacion()).average();
            if (promedioResenias.isPresent() && promedioResenias.getAsDouble() >= 4.5) {
                if (trofeo.isPresent()){
                    trofeoOtorgado.setUsuarioGanador(usuario);
                    trofeoOtorgado.setTrofeoOtorgado(trofeo.get());
                    trofeoRepository.save(trofeoOtorgado);

                    this.enviarNotificacionAUsuario(usuario, "Usted gano el trofeo al exito! Felicitaciones!");

                    System.out.println("Se otorga trofeo del exito");
                    return  trofeoOtorgado;
                }

            }
        }}

        trofeoOtorgado = null;
        return  trofeoOtorgado;

    };


    public Trofeo ganoTrofeosResenias(Usuario usuario) {
        List<Resenia> listaResenias = reseniasService.obtenerReseniasDeUsuario(usuario);
        Trofeo trofeoOtorgado = new Trofeo();
        Optional<TipoTrofeo> trofeo = tipoTrofeoRepository.findByNombreTrofeo("Trofeo a la reseña");


        boolean ganoElTrofeo = trofeoRepository.findByTrofeoOtorgadoAndUsuarioGanador(trofeo.get(),usuario).isPresent();

        if (!ganoElTrofeo){
        if (listaResenias.size() >= 1) {
            if (trofeo.isPresent()){
                trofeoOtorgado.setUsuarioGanador(usuario);
                trofeoOtorgado.setTrofeoOtorgado(trofeo.get());
                trofeoRepository.save(trofeoOtorgado);
                this.enviarNotificacionAUsuario(usuario, "Usted gano el trofeo a la reseña! Felicitaciones!");
                System.out.println("Se otorga trofeo a la reseña");
                return  trofeoOtorgado;
            }
        }
        }
        trofeoOtorgado = null;
        return  trofeoOtorgado;
    };


    private void enviarNotificacionAUsuario(Usuario usuario, String mensaje){
        notificacionService.enviarNotificacion(usuario, mensaje);
    }


}
