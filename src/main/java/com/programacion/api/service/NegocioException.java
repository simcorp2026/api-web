package com.programacion.api.service;

/**
 * Error de regla de negocio. Lleva el código HTTP que el controlador debe devolver,
 * sin depender de JAX-RS en la capa de servicio.
 */
public class NegocioException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final int codigoHttp;

    public NegocioException(int codigoHttp, String mensaje) {
        super(mensaje);
        this.codigoHttp = codigoHttp;
    }

    public int getCodigoHttp() {
        return codigoHttp;
    }
}
