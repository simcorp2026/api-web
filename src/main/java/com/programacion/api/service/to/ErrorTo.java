package com.programacion.api.service.to;

/** Cuerpo JSON de los errores de negocio. */
public class ErrorTo {
    private String mensaje;

    public ErrorTo() {
    }

    public ErrorTo(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
