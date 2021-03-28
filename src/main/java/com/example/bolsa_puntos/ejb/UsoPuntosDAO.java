package com.example.bolsa_puntos.ejb;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.example.bolsa_puntos.extra.UtilizarPuntos;
import com.example.bolsa_puntos.model.BolsaPunto;
import com.example.bolsa_puntos.model.ConceptoPunto;
import com.example.bolsa_puntos.model.DetalleUsoPunto;
import com.example.bolsa_puntos.model.UsoPunto;

@Stateless
public class UsoPuntosDAO {

    @PersistenceContext(name = "pruebaPU")
    private EntityManager em;

    @Inject
    ConceptoDAO conceptoDAO;

    public void usarPuntos(UtilizarPuntos utilizarPuntos) throws Exception{
        Query q = em.createQuery("select bp from BolsaPunto bp join bp.cliente c where c.id=:idCliente");
        q.setParameter("idCliente", utilizarPuntos.getId_cliente());

        try {
            @SuppressWarnings("unchecked")
            List<BolsaPunto> listBP = q.getResultList();
            //Chekeo de puntos(Funcion)

            Optional<ConceptoPunto> cpOptional = Optional.ofNullable(conceptoDAO.ver(utilizarPuntos.getId_concepto_uso()));
            if ( !cpOptional.isPresent()){
                throw new Exception("NO EXISTE CONCEPTO DE PUNTO CON ESE IDENTIFICADOR");
            }
            int sum = 0;
            UsoPunto usoPunto = establecerCabecera(listBP.get(0), cpOptional.get());
            for (BolsaPunto bp: listBP){
                if ( sum < usoPunto.getPuntajeUtilizado()){
                    sum = sum + establecerDetalle(bp, usoPunto);
                }
            }

        }
        catch(NoResultException nr){
            throw nr;
        }
    }

    public UsoPunto establecerCabecera(BolsaPunto bp, ConceptoPunto cp){
        UsoPunto usoPunto = new UsoPunto();
        usoPunto.setCliente(bp.getCliente());
        usoPunto.setPuntajeUtilizado(cp.getPuntosRequerido());
        usoPunto.setFecha(new Date());
        usoPunto.setConcepto(cp);
        return usoPunto;
    }
   
    public Integer establecerDetalle(BolsaPunto bp, UsoPunto usoPunto ){

        DetalleUsoPunto detalleUsoPunto = new DetalleUsoPunto();
        detalleUsoPunto.setUsoPunto(usoPunto);
        detalleUsoPunto.setPuntajeUtilizado(bp.getSaldo());

        //Armar validacion
    }
}
