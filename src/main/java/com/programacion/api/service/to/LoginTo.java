package com.programacion.api.service.to;

import jakarta.validation.constraints.NotBlank;

public class LoginTo {
    @NotBlank(message = "username es obligatorio")
    private String username;

    @NotBlank(message = "password es obligatorio")
    private String password;

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
}
