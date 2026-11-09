package com.programacion.api.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import io.quarkus.elytron.security.common.BcryptUtil;
import com.programacion.api.repository.IUsuarioRepository;
import com.programacion.api.repository.modelo.Usuario;
import com.programacion.api.service.to.NuevoUsuarioTo;
import com.programacion.api.service.to.UsuarioTo;

@ApplicationScoped
public class UsuarioServiceImpl implements IUsuarioService {

    static final String USERNAME_ADMIN = "admin";

    @Inject
    private IUsuarioRepository iUsuarioRepository;

    @Override
    @Transactional
    public UsuarioTo crear(NuevoUsuarioTo nuevo) {
        if (this.iUsuarioRepository.buscarPorUsername(nuevo.getUsername()).isPresent()) {
            throw new NegocioException(409, "El usuario ya existe: " + nuevo.getUsername());
        }
        Usuario usuario = new Usuario(nuevo.getUsername(), BcryptUtil.bcryptHash(nuevo.getPassword()), nuevo.getRol());
        this.iUsuarioRepository.insertar(usuario);
        return new UsuarioTo(usuario.getId(), usuario.getUsername(), usuario.getRol());
    }

    @Override
    @Transactional
    public void inicializarAdministrador(String password) {
        if (this.iUsuarioRepository.contar() > 0) {
            return;
        }
        this.iUsuarioRepository.insertar(
                new Usuario(USERNAME_ADMIN, BcryptUtil.bcryptHash(password), Roles.ADMIN));
    }
}
