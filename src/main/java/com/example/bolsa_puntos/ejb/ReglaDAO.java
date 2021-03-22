package com.example.bolsa_puntos.ejb;

import com.example.bolsa_puntos.model.ReglaPunto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ReglaDAO {

    @PersistenceContext(name = "pruebaPU")
    private EntityManager em;


    public void agregar(ReglaPunto regla) throws Exception{
        String msg = "YA EXISTE UNA REGLA SIMILAR";
        if(regla.getLimiteInferior() == null && regla.getLimiteSuperior() == null){//solo puede haber una regla general
            if(!lista().isEmpty()) throw new Exception(msg);
        }
        else{
            Query q = em.createQuery("select r from ReglaPunto r where (r.limiteInferior>=:inf " +
                    "and r.limiteInferior>=:sup) or (r.limiteInferior<=:inf and r.limiteSuperior>=:inf)");
            q.setParameter("inf",regla.getLimiteInferior());
            q.setParameter("sup",regla.getLimiteSuperior());
            if(!q.getResultList().isEmpty()) throw new Exception(msg);
        }
        lista();
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

    /**
     * Metodo para saber dado un monto cuantos puntos consegui con las reglas actuales
     * @param monto el monto a considerar para obtener puntos de
     * @return puntos conseguido por el monto
     */
    public Integer puntosConseguidos(int monto){
        int puntos=0;
        Query q = em.createQuery("select r from ReglaPunto r " +
                "where (r.limiteInferior<=:monto and r.limiteSuperior>=:monto)" +
                "or (r.limiteInferior is null and r.limiteSuperior>=:monto)" +
                "or (r.limiteInferior<=:monto and r.limiteSuperior is null)" +
                "or (r.limiteInferior is null and r.limiteSuperior is null)");
        q.setParameter("monto",monto);
        try{

            ReglaPunto r=(ReglaPunto) q.getSingleResult();
            puntos=monto/r.getEquivalencia();
        }
        catch (NoResultException ex){
            return 0;
        }
        catch(Exception ex){
            throw ex;
        }
        return puntos;
    }
}
