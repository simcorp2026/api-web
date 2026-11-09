package com.programacion.api.controller;

import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import com.programacion.api.service.IVentaService;
import com.programacion.api.service.Roles;
import com.programacion.api.service.to.RegistrarVentaTo;
import com.programacion.api.service.to.VentaTo;

@Path("/ventas")
@Tag(name = "Ventas", description = "Registro y consulta de ventas")
@RolesAllowed({ Roles.ADMIN, Roles.VENDEDOR })
public class VentaController {

    @Inject
    private IVentaService iVentaService;

    @POST
    @Operation(summary = "Registrar una venta", description = "Descuenta el stock y calcula el total con IVA")
    @APIResponse(responseCode = "201", description = "Venta registrada")
    @APIResponse(responseCode = "400", description = "Datos de entrada inválidos o producto repetido")
    @APIResponse(responseCode = "404", description = "Producto no encontrado")
    @APIResponse(responseCode = "409", description = "Stock insuficiente")
    public Response registrar(@Valid RegistrarVentaTo venta) {
        VentaTo creada = this.iVentaService.registrar(venta);
        return Response.status(Response.Status.CREATED).entity(creada).build();
    }

    @GET
    @Operation(summary = "Listar ventas")
    public List<VentaTo> buscarTodas() {
        return this.iVentaService.buscarTodas();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener una venta por id")
    @APIResponse(responseCode = "404", description = "Venta no encontrada")
    public VentaTo buscar(@PathParam("id") Integer id) {
        return this.iVentaService.buscar(id);
    }
}
