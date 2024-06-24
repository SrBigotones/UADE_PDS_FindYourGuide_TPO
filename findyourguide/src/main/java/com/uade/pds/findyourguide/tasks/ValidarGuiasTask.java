package com.uade.pds.findyourguide.tasks;

import com.uade.pds.findyourguide.enums.EstadoUsuario;
import com.uade.pds.findyourguide.model.user.Usuario;
import com.uade.pds.findyourguide.model.user.UsuarioGuia;
import com.uade.pds.findyourguide.service.UsuarioGuiaService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValidarGuiasTask {

    @Autowired private UsuarioGuiaService usuarioGuiaService;

    private IVerificacionGuiaAdapter verificacionGuiaAdapter;

    @PostConstruct
    void init() {
        verificacionGuiaAdapter = new VerificacionIA();
    }

    @Scheduled(fixedDelay = 1 * 60 * 1000)
    public void validarGuias() {
        System.out.println("Validando guias");
        List<UsuarioGuia> usuarios = usuarioGuiaService.buscarUsuariosPendienteValidacion();

        List<UsuarioGuia> validatedUsers = new ArrayList<>();
        for (UsuarioGuia usuario : usuarios) {
            boolean validacion = validarUsuario(usuario);
            if (validacion) {
                usuario.setEstado(EstadoUsuario.ACTIVO);
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
