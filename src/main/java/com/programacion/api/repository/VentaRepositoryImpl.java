package com.programacion.api.repository;

import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import com.programacion.api.repository.modelo.Venta;

@Transactional
@ApplicationScoped
public class VentaRepositoryImpl implements IVentaRepository {

    private static final String SELECT_CON_DETALLES = "SELECT DISTINCT v FROM Venta v "
            + "LEFT JOIN FETCH v.detalles d LEFT JOIN FETCH d.producto ";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insertar(Venta venta) {
        this.entityManager.persist(venta);
    }

    @Override
    public List<Venta> seleccionarTodas() {
        return this.entityManager.createQuery(SELECT_CON_DETALLES + "ORDER BY v.id", Venta.class).getResultList();
    }

    @Override
    public Optional<Venta> seleccionar(Integer id) {
        return this.entityManager.createQuery(SELECT_CON_DETALLES + "WHERE v.id = :id", Venta.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }
}
