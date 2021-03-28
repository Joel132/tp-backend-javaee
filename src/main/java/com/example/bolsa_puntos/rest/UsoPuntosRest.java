package com.example.bolsa_puntos.rest;

import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.example.bolsa_puntos.ejb.UsoPuntosDAO;
import com.example.bolsa_puntos.extra.Respuesta;
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
        Respuesta respuesta = usoPuntosDAO.usarPuntos(utilizarPuntos);
        return Response.ok(respuesta).build();
    }

    @GET
    @Path("/")
    public Response obtenerUsoPuntos(@QueryParam("id_cliente") int id_cliente, 
    @QueryParam("fecha_uso") Date fecha_uso, @QueryParam("id_concepto_uso") int id_concepto ){
        return Response.ok().build();
    }
    
}
