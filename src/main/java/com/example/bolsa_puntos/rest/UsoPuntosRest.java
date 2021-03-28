package com.example.bolsa_puntos.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.example.bolsa_puntos.ejb.UsoPuntosDAO;
import com.example.bolsa_puntos.extra.UtilizarPuntos;

@Path("/usoPuntos")
@Consumes("application/json")
@Produces("application/json")
public class UsoPuntosRest {

    @Inject
    private UsoPuntosDAO usoPuntosDAO;

    @POST
    @Path("/")
    public Response utilizar(UtilizarPuntos utilizarPuntos) throws Exception{
        usoPuntosDAO.usarPuntos(utilizarPuntos);
        return Response.ok().build();
    }
    
}
