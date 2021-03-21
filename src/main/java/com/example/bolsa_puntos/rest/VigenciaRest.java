package com.example.bolsa_puntos.rest;

import com.example.bolsa_puntos.ejb.VigenciaDAO;
import com.example.bolsa_puntos.model.VigenciaPunto;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/vigencia")
@Produces("application/json")
@Consumes("application/json")
public class VigenciaRest {

    @Inject
    private VigenciaDAO vigenciaDAO;

    @GET
    @Path("/")
    public Response listar(){
        return Response.ok(vigenciaDAO.lista()).build();
    }

    @POST
    @Path("/")
    public Response agregar(VigenciaPunto vigencia){
        vigenciaDAO.agregar(vigencia);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(VigenciaPunto vigencia, @PathParam("id") int id){
        vigencia.setId(id);
        vigenciaDAO.actualizar(vigencia);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") int id){
        vigenciaDAO.eliminar(id);
        return Response.ok().build();
    }
}
