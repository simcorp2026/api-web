package com.programacion.api.service.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class NuevoUsuarioTo {
    @NotBlank(message = "username es obligatorio")
    @Size(min = 3, max = 50, message = "username debe tener entre 3 y 50 caracteres")
    private String username;

    @NotBlank(message = "password es obligatorio")
    @Size(min = 8, message = "password debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "rol es obligatorio")
    @Pattern(regexp = "ADMIN|VENDEDOR", message = "rol debe ser ADMIN o VENDEDOR")
    private String rol;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
