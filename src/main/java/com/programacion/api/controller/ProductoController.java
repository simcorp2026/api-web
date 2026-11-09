package com.programacion.api.controller;

import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import com.programacion.api.service.IProductoService;
import com.programacion.api.service.Roles;
import com.programacion.api.service.to.ProductoTo;

@Path("/productos")
@Tag(name = "Productos", description = "Catálogo y stock de productos")
@RolesAllowed({ Roles.ADMIN, Roles.VENDEDOR })
public class ProductoController {

    @Inject
    private IProductoService iProductoService;

    @POST
    @Path("")
    @Operation(summary = "Crear un producto o sumar stock", description = "Sin id crea el producto; con id suma stock al existente")
    @APIResponse(responseCode = "404", description = "El id indicado no existe")
    @RolesAllowed(Roles.ADMIN)
    public void guardar(ProductoTo producto) {
        this.iProductoService.guardar(producto);

    }
    @GET
    @Path("")
    @Operation(summary = "Listar productos")
    public List<ProductoTo> buscarTodos() {
        return this.iProductoService.buscarTodos();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener un producto por id")
    public ProductoTo buscar(@PathParam("id") Integer id) {
        return this.iProductoService.buscar(id);
    }
}