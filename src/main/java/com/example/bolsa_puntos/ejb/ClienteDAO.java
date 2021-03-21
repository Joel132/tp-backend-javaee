package com.example.bolsa_puntos.ejb;

import com.example.bolsa_puntos.model.Cliente;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ClienteDAO {

    @PersistenceContext(name = "pruebaPU")
    private EntityManager em;


    public void agregar(Cliente cliente){
        em.persist(cliente);
    }

    public List<Cliente> lista(){
        Query q = em.createQuery("select c from Cliente  c");
        return (List<Cliente>) q.getResultList();
    }
}
