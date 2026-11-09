package com.programacion.api.repository;

import java.util.List;
import java.util.Optional;

import com.programacion.api.repository.modelo.Venta;

public interface IVentaRepository {
    public void insertar(Venta venta);

    public List<Venta> seleccionarTodas();

    public Optional<Venta> seleccionar(Integer id);
}
