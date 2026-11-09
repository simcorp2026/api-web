package com.programacion.api.controller;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import com.programacion.api.service.NegocioException;
import com.programacion.api.service.to.ErrorTo;

@Provider
public class NegocioExceptionMapper implements ExceptionMapper<NegocioException> {

    @Override
    public Response toResponse(NegocioException e) {
        return Response.status(e.getCodigoHttp())
                .entity(new ErrorTo(e.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
