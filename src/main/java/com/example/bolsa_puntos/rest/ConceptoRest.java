package com.example.bolsa_puntos.rest;

import com.example.bolsa_puntos.ejb.ConceptoDAO;
import com.example.bolsa_puntos.model.ConceptoPunto;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/concepto")
@Produces("application/json")
@Consumes("application/json")
public class ConceptoRest {

    @Inject
    private ConceptoDAO conceptoDAO;

    @GET
    @Path("/")
    public Response listar(){
        return Response.ok(conceptoDAO.lista()).build();
    }

    @POST
    @Path("/")
    public Response agregar(ConceptoPunto concepto){
        conceptoDAO.agregar(concepto);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(ConceptoPunto concepto, @PathParam("id") int id){
        concepto.setId(id);
        conceptoDAO.actualizar(concepto);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") int id){
        conceptoDAO.eliminar(id);
        return Response.ok().build();
    }
}
