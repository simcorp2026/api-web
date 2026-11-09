package com.programacion.api.service;

import com.programacion.api.service.to.NuevoUsuarioTo;
import com.programacion.api.service.to.UsuarioTo;

public interface IUsuarioService {
    public UsuarioTo crear(NuevoUsuarioTo usuario);

    /** Crea el usuario administrador inicial si todavía no existe ningún usuario. */
    public void inicializarAdministrador(String password);
}
