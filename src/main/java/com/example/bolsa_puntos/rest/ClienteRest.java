package com.example.bolsa_puntos.rest;

import com.example.bolsa_puntos.ejb.ClienteDAO;
import com.example.bolsa_puntos.model.Cliente;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Path("/cliente")
@Produces("application/json")
@Consumes("application/json")
public class ClienteRest {

    @Inject
    private ClienteDAO clienteDAO;

    @GET
    @Path("/")
    public Response listar(@QueryParam("nombre")String nombre,
                           @QueryParam("apellido") String apellido,
                           @QueryParam("fecha")String fecha
                           ){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaIn;
        try {
            fechaIn = sdf.parse(fecha);
        }catch(ParseException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        if(nombre != null || apellido != null || fecha != null){
            Cliente cli = new Cliente();
            cli.setNombre(nombre);
            cli.setApellido(apellido);
            cli.setFechaNacimiento(fechaIn);
            return Response.ok(clienteDAO.lista(cli)).build();
        }
        return Response.ok(clienteDAO.lista()).build();
    }

    @POST
    @Path("/")
    public Response agregar(Cliente cliente){
        clienteDAO.agregar(cliente);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(Cliente cliente, @PathParam("id") int id){
        cliente.setId(id);
        clienteDAO.actualizar(cliente);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") int id){
        clienteDAO.eliminar(id);
        return Response.ok().build();
    }

    @GET
    @Path("/punto-vencido")
    public Response puntoVencido(@QueryParam("dias")int dias
    ){

        return Response.ok(clienteDAO.lista_vencimiento(dias)).build();
    }

}
