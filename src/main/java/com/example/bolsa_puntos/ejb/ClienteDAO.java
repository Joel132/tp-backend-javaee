package com.example.bolsa_puntos.ejb;

import com.example.bolsa_puntos.model.BolsaPunto;
import com.example.bolsa_puntos.model.Cliente;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
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

    public void eliminar(int id){
        Cliente cliente=em.find(Cliente.class,id);
        em.remove(cliente);
    }

    public void actualizar(Cliente cliente){
        em.merge(cliente);
    }

    public Cliente ver(int id){
        return em.find(Cliente.class,id);
    }

    public List<Cliente> lista(Cliente cliente){
        Query q = em.createQuery("select c from Cliente  c where c.nombre like :nom or c.apellido like :ape or c.fechaNacimiento=:fec");
        q.setParameter("nom", like(cliente.getNombre()));
        q.setParameter("ape", like(cliente.getApellido()));
        q.setParameter("fec", cliente.getFechaNacimiento());
        return (List<Cliente>) q.getResultList();
    }

    private String like(String txt){
        if(txt == null || txt.trim().isEmpty()) return null;
        return "%" + txt +"%";
    }

    public List<Cliente> lista_vencimiento(int dias){
        LocalDate lc = LocalDate.now().plusDays(dias);
        Query q = em.createQuery("select new com.example.bolsa_puntos.model.Cliente(b.cliente.id,b.cliente.nombre,b.cliente.apellido,b.cliente.nroDocumento,b.cliente.tipoDocumento,b.cliente.nacionalidad,b.cliente.email,b.cliente.telefono,b.cliente.fechaNacimiento,sum(b.saldo)) from BolsaPunto b where b.fechaVencimiento<=:fec and b.saldo <> 0 GROUP BY b.cliente");
        q.setParameter("fec",lc);
        return (List<Cliente>) q.getResultList();
    }
}
