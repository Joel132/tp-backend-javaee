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

import com.example.bolsa_puntos.extra.Respuesta;
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

    /**
     * Utilizacion de puntos
     * 
     * @param utilizarPuntos
     * @throws Exception
     */

    public Respuesta usarPuntos(UtilizarPuntos utilizarPuntos){
        Respuesta rp = new Respuesta();
        Query q = em.createQuery("select bp from BolsaPunto bp join bp.cliente c where c.id=:idCliente");
        q.setParameter("idCliente", utilizarPuntos.getId_cliente());

        try {
            @SuppressWarnings("unchecked")
            List<BolsaPunto> listBP = q.getResultList();

            if (listBP.isEmpty()){ 
                rp.setMsg("EL CLIENTE NO TIENE BOLSAS ASIGNADA ACTUALMENTE O IDENTIFICADOR DEL CLIENTE NO EXISTE");
                return rp;
            }
            //Chekeo de puntos(Funcion)

            Optional<ConceptoPunto> cpOptional = Optional.ofNullable(conceptoDAO.ver(utilizarPuntos.getId_concepto_uso()));
            if ( !cpOptional.isPresent()){
               rp.setMsg("NO EXISTE CONCEPTO DE PUNTO CON ESE IDENTIFICADOR");
               return rp;
            }
            
            UsoPunto usoPunto = establecerCabecera(listBP.get(0), cpOptional.get());
            int puntos_faltantes = usoPunto.getPuntajeUtilizado();
            for (BolsaPunto bp: listBP){
                if ( puntos_faltantes > 0 ){
                    puntos_faltantes =  establecerDetalle(bp, usoPunto, puntos_faltantes);
                }
            }

            rp.setMsg("UTILIZACIÓN DE PUNTOS COMPLETADA");
            return rp;

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
   
    public Integer establecerDetalle(BolsaPunto bp, UsoPunto usoPunto, int puntos ){
        int saldo_punto_utilizado;
        DetalleUsoPunto detalleUsoPunto = new DetalleUsoPunto();

        if (puntos >= bp.getSaldo() ) {
            saldo_punto_utilizado = bp.getSaldo();
        }
        else{
            saldo_punto_utilizado = puntos;
        }
        
        puntos = puntos - saldo_punto_utilizado;
        bp.setPuntajeUtilizado(saldo_punto_utilizado);
        bp.setSaldo(bp.getPuntajeAsignado() - bp.getPuntajeUtilizado());
        //BolsaDao actualizar;
        detalleUsoPunto.setUsoPunto(usoPunto);
        detalleUsoPunto.setPuntajeUtilizado(saldo_punto_utilizado);
        detalleUsoPunto.setBolsa(bp);
        em.persist(detalleUsoPunto);

        return puntos;
    }
}
