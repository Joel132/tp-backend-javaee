package com.example.bolsa_puntos.rest;

import com.example.bolsa_puntos.ejb.BolsaPuntoDAO;
import com.example.bolsa_puntos.ejb.ClienteDAO;
import com.example.bolsa_puntos.ejb.ReglaDAO;
import com.example.bolsa_puntos.ejb.VigenciaDAO;
import com.example.bolsa_puntos.model.BolsaPunto;
import com.example.bolsa_puntos.model.VigenciaPunto;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("/bolsa-puntos")
@Produces("application/json")
@Consumes("application/json")
public class BolsaPuntosRest {

    @Inject
    private BolsaPuntoDAO bolsaPuntoDAO;

    @Inject
    private ReglaDAO reglaDAO;

    @Inject
    private VigenciaDAO vigenciaDAO;

    @Inject
    private ClienteDAO clienteDAO;

    @GET
    @Path("/")
    public Response listar(){
        return Response.ok(bolsaPuntoDAO.lista()).build();
    }

    @GET
    @Path("/{id}")
    public Response ver(@PathParam("id") int id){
        return Response.ok(bolsaPuntoDAO.ver(id)).build();
    }

    @POST
    @Path("/")
    public Response agregar(BolsaPunto bolsa){
        bolsaPuntoDAO.agregar(bolsa);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(BolsaPunto bolsa, @PathParam("id") int id){
        bolsa.setId(id);
        bolsaPuntoDAO.actualizar(bolsa);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") int id){
        bolsaPuntoDAO.eliminar(id);
        return Response.ok().build();
    }

    @POST
    @Path("/cargar")
    public Response cargaPuntos(@QueryParam("idCliente") int idCliente, @QueryParam("monto") int monto){
        BolsaPunto bolsa = new BolsaPunto();
        int puntos = reglaDAO.puntosConseguidos(monto);
        Date fechaHoy = new Date();
        bolsa.setFechaAsignacion(fechaHoy);
        bolsa.setFechaVencimiento(vigenciaDAO.fechaVencimiento(fechaHoy));
        bolsa.setPuntajeUtilizado(0);
        bolsa.setPuntajeAsignado(puntos);
        bolsa.setSaldo(puntos);
        bolsa.setMonto(monto);
        bolsa.setCliente(clienteDAO.ver(idCliente));
        return agregar(bolsa);
    }

    @GET
    @Path("/cliente/{id}")
    public Response bolsasCliente(@PathParam("id") int idCliente){
        return Response.ok(bolsaPuntoDAO.bolsasCliente(clienteDAO.ver(idCliente))).build();
    }

    @GET
    @Path("/puntos-cliente/{id}")
    public Response puntosCliente(@PathParam("id") int idCliente){
        return Response.ok(bolsaPuntoDAO.puntosCliente(clienteDAO.ver(idCliente))).build();
    }
}
