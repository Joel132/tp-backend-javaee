package com.example.bolsa_puntos.ejb;

import com.example.bolsa_puntos.model.ReglaPunto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ReglaDAO {

    @PersistenceContext(name = "pruebaPU")
    private EntityManager em;


    public void agregar(ReglaPunto regla){
        em.persist(regla);
    }

    public List<ReglaPunto> lista(){
        Query q = em.createQuery("select r from ReglaPunto r");
        return (List<ReglaPunto>) q.getResultList();
    }

    public void eliminar(int id){
        ReglaPunto regla=em.find(ReglaPunto.class,id);
        em.remove(regla);
    }

    public void actualizar(ReglaPunto regla){
        em.merge(regla);
    }

    public ReglaPunto ver(int id){
        return em.find(ReglaPunto.class,id);
    }
}
