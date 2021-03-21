package com.example.bolsa_puntos.rest;

import com.example.bolsa_puntos.ejb.ReglaDAO;
import com.example.bolsa_puntos.model.ReglaPunto;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/regla")
@Produces("application/json")
@Consumes("application/json")
public class ReglaRest {

    @Inject
    private ReglaDAO reglaDAO;

    @GET
    @Path("/")
    public Response listar(){
        return Response.ok(reglaDAO.lista()).build();
    }

    @POST
    @Path("/")
    public Response agregar(ReglaPunto regla){
        reglaDAO.agregar(regla);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(ReglaPunto regla, @PathParam("id") int id){
        regla.setId(id);
        reglaDAO.actualizar(regla);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") int id){
        reglaDAO.eliminar(id);
        return Response.ok().build();
    }
}
