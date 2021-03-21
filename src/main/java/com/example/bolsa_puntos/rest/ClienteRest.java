package com.example.bolsa_puntos.rest;

import com.example.bolsa_puntos.ejb.ClienteDAO;
import com.example.bolsa_puntos.model.Cliente;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/cliente")
@Produces("application/json")
@Consumes("application/json")
public class ClienteRest {

    @Inject
    private ClienteDAO clienteDAO;

    @GET
    @Path("/")
    public Response listar(){
        return Response.ok(clienteDAO.lista()).build();
    }

    @POST
    @Path("/")
    public Response agregar(Cliente cliente){
        clienteDAO.agregar(cliente);
        return Response.ok().build();
    }
}
