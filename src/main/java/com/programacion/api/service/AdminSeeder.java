package com.programacion.api.service;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.runtime.Startup;

/**
 * Al arrancar, crea el usuario "admin" si la tabla de usuarios está vacía.
 * La contraseña inicial viene de la variable de entorno ADMIN_PASSWORD.
 */
@Startup
@ApplicationScoped
public class AdminSeeder {

    @Inject
    private IUsuarioService iUsuarioService;

    @ConfigProperty(name = "app.admin.password")
    String adminPassword;

    @PostConstruct
    void init() {
        this.iUsuarioService.inicializarAdministrador(adminPassword);
    }
}
