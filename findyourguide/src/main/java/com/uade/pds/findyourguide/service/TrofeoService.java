package com.uade.pds.findyourguide.service;


import com.uade.pds.findyourguide.model.trofeo.TipoTrofeo;
import com.uade.pds.findyourguide.model.trofeo.Trofeo;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.repository.TipoTrofeoRepository;
import com.uade.pds.findyourguide.repository.TrofeoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class TrofeoService {


    @Autowired
    private TrofeoRepository trofeoRepository;

    @Autowired
    private TipoTrofeoRepository tipoTrofeoRepository;


    public Trofeo otorgarTrofeo(Usuario usuarioGanador, String nombreTrofeo) {
        Optional<TipoTrofeo> tipo_trofeo = tipoTrofeoRepository.findByNombre(nombreTrofeo);
        if (tipo_trofeo.isEmpty()){
            return null;
        }
        Trofeo trofeo = new Trofeo();
        trofeo.setTrofeoOtorgado(tipo_trofeo.get());
        trofeo.setUsuarioGanador(usuarioGanador);

        return trofeoRepository.save(trofeo);
    }


}
