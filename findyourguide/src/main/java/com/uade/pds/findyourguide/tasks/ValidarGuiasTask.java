package com.uade.pds.findyourguide.tasks;

import com.uade.pds.findyourguide.enums.EstadoUsuario;
import com.uade.pds.findyourguide.enums.MetodoNotificacion;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.model.user.UsuarioGuia;
import com.uade.pds.findyourguide.service.UsuarioGuiaService;
import com.uade.pds.findyourguide.service.notificacion.NotificacionService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValidarGuiasTask {

    @Autowired private UsuarioGuiaService usuarioGuiaService;
    @Autowired private NotificacionService notificacionService;

    private IVerificacionGuiaAdapter verificacionGuiaAdapter;

    @PostConstruct
    void init() {
        verificacionGuiaAdapter = new VerificacionIA();
    }

    @Scheduled(fixedDelay = 1 * 60 * 1000)
    public void validarGuias() {
        System.out.println("Task: Validando guias");
        List<UsuarioGuia> usuarios = usuarioGuiaService.buscarUsuariosPendienteValidacion();

        List<UsuarioGuia> validatedUsers = new ArrayList<>();
        for (UsuarioGuia usuario : usuarios) {
            boolean validacion = validarUsuario(usuario);
            if (validacion) {
                usuario.setEstado(EstadoUsuario.ACTIVO);

                String mensaje = String.format("Felicitaciones %s! Su cuenta ya se encuentra activa, ya puede brindar sus servicios en la herramienta", usuario.getNombre());
                notificacionService.enviarNotificacion(usuario, mensaje, MetodoNotificacion.PUSH);
                validatedUsers.add(usuario);
            }
        }

        saveValdatedUsers(validatedUsers);
    }

    private void saveValdatedUsers(List<UsuarioGuia> validatedUsers) {
        usuarioGuiaService.saveAll(validatedUsers);
    }

    private boolean validarUsuario(UsuarioGuia usuario) {
        return verificacionGuiaAdapter.verificar(usuario);
    }
}
