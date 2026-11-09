package com.programacion.api.service.to;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

/** Datos de entrada para registrar una venta. */
public class RegistrarVentaTo {
    @NotBlank(message = "cedulaCliente es obligatoria")
    @Pattern(regexp = "\\d{10}", message = "cedulaCliente debe tener 10 digitos")
    private String cedulaCliente;

    @NotEmpty(message = "la venta debe tener al menos un detalle")
    @Valid
    private List<DetalleVentaTo> detalles;

    public String getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(String cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    public List<DetalleVentaTo> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaTo> detalles) {
        this.detalles = detalles;
    }
}
