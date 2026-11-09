package com.programacion.api.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import com.programacion.api.service.IAuthService;
import com.programacion.api.service.to.LoginTo;
import com.programacion.api.service.to.TokenTo;

@Path("/auth")
@Tag(name = "Autenticación", description = "Obtención de tokens JWT")
public class AuthController {

    @Inject
    private IAuthService iAuthService;

    @POST
    @Path("/login")
    @PermitAll
    @Operation(summary = "Iniciar sesión y obtener un JWT")
    @APIResponse(responseCode = "200", description = "Token emitido")
    @APIResponse(responseCode = "401", description = "Credenciales inválidas")
    public TokenTo login(@Valid LoginTo login) {
        return this.iAuthService.login(login.getUsername(), login.getPassword());
    }
}
