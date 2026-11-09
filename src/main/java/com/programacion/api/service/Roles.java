package com.programacion.api.service;

/** Roles de la aplicación. Se usan en las anotaciones @RolesAllowed de los controladores. */
public final class Roles {
    public static final String ADMIN = "ADMIN";
    public static final String VENDEDOR = "VENDEDOR";

    private Roles() {
    }
}
