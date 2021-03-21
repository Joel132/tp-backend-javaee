package com.example.bolsa_puntos.ejb;

import com.example.bolsa_puntos.model.VigenciaPunto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class VigenciaDAO {

    @PersistenceContext(name = "pruebaPU")
    private EntityManager em;


    public void agregar(VigenciaPunto vigencia){
        em.persist(vigencia);
    }

    public List<VigenciaPunto> lista(){
        Query q = em.createQuery("select v from VigenciaPunto v");
        return (List<VigenciaPunto>) q.getResultList();
    }

    public void eliminar(int id){
        VigenciaPunto vigencia=em.find(VigenciaPunto.class,id);
        em.remove(vigencia);
    }

    public void actualizar(VigenciaPunto vigencia){
        em.merge(vigencia);
    }

    public VigenciaPunto ver(int id){
        return em.find(VigenciaPunto.class,id);
    }
}
