package com.programacion.api.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import com.programacion.api.service.IUsuarioService;
import com.programacion.api.service.Roles;
import com.programacion.api.service.to.NuevoUsuarioTo;
import com.programacion.api.service.to.UsuarioTo;

@Path("/usuarios")
@Tag(name = "Usuarios", description = "Administración de usuarios")
@RolesAllowed(Roles.ADMIN)
public class UsuarioController {

    @Inject
    private IUsuarioService iUsuarioService;

    @POST
    @Operation(summary = "Crear un usuario (solo ADMIN)")
    @APIResponse(responseCode = "201", description = "Usuario creado")
    @APIResponse(responseCode = "409", description = "El username ya existe")
    public Response crear(@Valid NuevoUsuarioTo usuario) {
        UsuarioTo creado = this.iUsuarioService.crear(usuario);
        return Response.status(Response.Status.CREATED).entity(creado).build();
    }
}
