package com.example.bolsa_puntos.ejb;

import com.example.bolsa_puntos.model.ConceptoPunto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ConceptoDAO {

    @PersistenceContext(name = "pruebaPU")
    private EntityManager em;


    public void agregar(ConceptoPunto concepto){
        em.persist(concepto);
    }

    public List<ConceptoPunto> lista(){
        Query q = em.createQuery("select c from ConceptoPunto  c");
        return (List<ConceptoPunto>) q.getResultList();
    }

    public void eliminar(int id){
        ConceptoPunto concepto=em.find(ConceptoPunto.class,id);
        em.remove(concepto);
    }

    public void actualizar(ConceptoPunto concepto){
        em.merge(concepto);
    }

    public ConceptoPunto ver(int id){
        return em.find(ConceptoPunto.class,id);
    }
}
