package com.programacion.api.service;

import java.util.List;

import com.programacion.api.service.to.RegistrarVentaTo;
import com.programacion.api.service.to.VentaTo;

public interface IVentaService {
    public VentaTo registrar(RegistrarVentaTo venta);

    public List<VentaTo> buscarTodas();

    public VentaTo buscar(Integer id);
}
