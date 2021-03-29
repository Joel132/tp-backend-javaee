package com.example.bolsa_puntos.ejb;

import com.example.bolsa_puntos.model.BolsaPunto;
import com.example.bolsa_puntos.model.Cliente;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Stateless
public class BolsaPuntoDAO {

    @PersistenceContext(name = "pruebaPU")
    private EntityManager em;

    public void agregar(BolsaPunto bolsa){
        em.persist(bolsa);
    }

    public List<BolsaPunto> lista(int idCliente, int puntosDesde, int puntosHasta) throws Exception{
        if(idCliente != 0 && puntosDesde != 0 && puntosHasta != 0) {
            Query q = em.createQuery("select b from BolsaPunto b " +
                    "where b.cliente.id=:cli " +
                    "and b.puntajeAsignado>=:minPuntos " +
                    "and b.puntajeAsignado<=:maxPuntos");
            q.setParameter("cli", idCliente);
            q.setParameter("minPuntos", puntosDesde);
            q.setParameter("maxPuntos", puntosHasta);
            return (List<BolsaPunto>) q.getResultList();
        }else if(idCliente != 0 && puntosDesde != 0 && puntosHasta == 0){
            Query q = em.createQuery("select b from BolsaPunto b " +
                    "where b.cliente.id=:cli " +
                    "and b.puntajeAsignado>=:minPuntos");
            q.setParameter("cli", idCliente);
            q.setParameter("minPuntos", puntosDesde);
            return (List<BolsaPunto>) q.getResultList();
        }else if(idCliente != 0 && puntosDesde == 0 && puntosHasta != 0){
            Query q = em.createQuery("select b from BolsaPunto b " +
                    "where b.cliente.id=:cli " +
                    "and b.puntajeAsignado<=:maxPuntos");
            q.setParameter("cli", idCliente);
            q.setParameter("maxPuntos", puntosHasta);
            return (List<BolsaPunto>) q.getResultList();
        }else if(idCliente != 0 && puntosDesde == 0 && puntosHasta == 0){
            Query q = em.createQuery("select b from BolsaPunto b" +
                    " where b.cliente.id=:cli");
            q.setParameter("cli", idCliente);
            return (List<BolsaPunto>) q.getResultList();
        }else if(idCliente == 0 && puntosDesde != 0 && puntosHasta != 0){
            Query q = em.createQuery("select b from BolsaPunto b " +
                    "where b.puntajeAsignado>=:minPuntos " +
                    "and b.puntajeAsignado<=:maxPuntos");
            q.setParameter("minPuntos", puntosDesde);
            q.setParameter("maxPuntos", puntosHasta);
            return (List<BolsaPunto>) q.getResultList();
        }else if(idCliente == 0 && puntosDesde != 0 && puntosHasta == 0){
            Query q = em.createQuery("select b from BolsaPunto b " +
                    "where b.puntajeAsignado>=:minPuntos");
            q.setParameter("minPuntos", puntosDesde);
            return (List<BolsaPunto>) q.getResultList();
        }else if(idCliente == 0 && puntosDesde == 0 && puntosHasta != 0){
            Query q = em.createQuery("select b from BolsaPunto b " +
                    "where b.puntajeAsignado<=:maxPuntos");
            q.setParameter("maxPuntos", puntosHasta);
            return (List<BolsaPunto>) q.getResultList();
        }else if(idCliente == 0 && puntosDesde == 0 && puntosHasta == 0) {
            Query q = em.createQuery("select b from BolsaPunto b");
            return (List<BolsaPunto>) q.getResultList();
        }else{
            throw new Exception("PARAMETROS NULOS");
        }
    }

    public void eliminar(int id){
        BolsaPunto bolsa = em.find(BolsaPunto.class,id);
        em.remove(bolsa);
    }

    public void actualizar(BolsaPunto bolsa){
        em.merge(bolsa);
    }
    public BolsaPunto ver(int id){
        return em.find(BolsaPunto.class,id);
    }

    /**
     * Obtiene la Bolsa de puntos por Cliente
     *
     * @param cliente El cliente del cual se quiere las bolsas
     * @return Lista de BolsaPuntos con todas las bolsas del cliente
     */
    public List<BolsaPunto> bolsasCliente(Cliente cliente){
        Query q = em.createQuery("select b from BolsaPunto b where b.cliente=:cli");
        q.setParameter("cli",cliente);
        System.out.println(new Date());
        return (List<BolsaPunto>) q.getResultList();
    }

    /**
     * El total de puntos que tiene actualmente el Cliente
     *
     * @param cliente El cliente del que se quiere saber sus puntos
     * @return totalPuntos, un entero con la suma de los puntos no vencidos disponibles
     */
    public int puntosCliente(Cliente cliente){
        List<BolsaPunto> bolsas = bolsasCliente(cliente);
        int totalPuntos = 0;
        Date hoy = new Date();
        for(BolsaPunto bolsa: bolsas){
            if(bolsa.getFechaVencimiento().after(hoy)){
                totalPuntos += bolsa.getSaldo();
            }
        }
        return totalPuntos;
    }

    /**
     * Un verificador si un cliente tiene al menos la cantidad de puntos requeridos
     *
     * @param puntosRequeridos La cantidad de puntos que se quiere corroborar si el cliente tiene
     * @param cliente El cliente a corroborar
     * @return True si el cliente cuenta con al menos la cantidad requerida, False caso contrario
     */
    public boolean chequeoPuntos(int puntosRequeridos, Cliente cliente){
        int saldoCliente = puntosCliente(cliente);
        return saldoCliente >= puntosRequeridos;
    }

}
