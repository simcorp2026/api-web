package com.programacion.api.service.to;

/** Usuario en la salida de la API. Nunca incluye la contraseña ni su hash. */
public class UsuarioTo {
    private Integer id;
    private String username;
    private String rol;

    public UsuarioTo() {
    }

    public UsuarioTo(Integer id, String username, String rol) {
        this.id = id;
        this.username = username;
        this.rol = rol;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRol() {
        return rol;
    }
}
