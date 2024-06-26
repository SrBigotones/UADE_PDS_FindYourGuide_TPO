package com.uade.pds.findyourguide.tasks;


import com.uade.pds.findyourguide.model.contrato.Contrato;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.service.ContratoService;
import com.uade.pds.findyourguide.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class RecordatorioReseniaTask {

    @Autowired
    private ContratoService contratoService;
    @Autowired private UsuarioService usuarioService;

    private static final long DIAS_FINALIZADO = 5;

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void buscarPendientesResenias() {
        LocalDate fechaObjetivo = LocalDate.now().minusDays(DIAS_FINALIZADO);
        List<Contrato> contratosEncontrados = contratoService.obtenerContratosConFechaFin(fechaObjetivo);
        for (Contrato c : contratosEncontrados) {
            Optional<Usuario> usuarioEncontrado = usuarioService.findUserById(c.getUsuarioContratante().getId());
            // ACA LE AVISAMOS AL MAIL POR ACCION DIVINA
            usuarioEncontrado.ifPresent(usuario -> System.out.println(usuario.getEmail()));
        }
    }
}
