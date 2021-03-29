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
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

@Stateless
public class UsoPuntosDAO {

    @PersistenceContext(name = "pruebaPU")
    private EntityManager em;

    @Inject
    ConceptoDAO conceptoDAO;

    @Inject 
    BolsaPuntoDAO bolsaPuntoDAO;

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

            /**Validar si la lista esta vacia 
            Puede ser que el cliente no tenga asignada bolsas
            O que el identificador del cliente no exista**/
            if (listBP.isEmpty()){ 
                rp.setMsg("EL CLIENTE NO TIENE BOLSAS ASIGNADA ACTUALMENTE O IDENTIFICADOR DEL CLIENTE NO EXISTE");
                return rp;
            }

            /** Validar si el identificador del concepto de uso existe*/
            Optional<ConceptoPunto> cpOptional = Optional.ofNullable(conceptoDAO.ver(utilizarPuntos.getId_concepto_uso()));
            if ( !cpOptional.isPresent()){
               rp.setMsg("NO EXISTE CONCEPTO DE PUNTO CON ESE IDENTIFICADOR");
               return rp;
            }

            /** Chequeo de puntos */
            boolean chequeo = bolsaPuntoDAO.chequeoPuntos(cpOptional.get().getPuntosRequerido(), listBP.get(0).getCliente());
            if ( !chequeo ){
                rp.setMsg("EL CLIENTE NO TIENE SUFICIENTES PUNTOS PARA CONSEGUIR EL PREMIO");
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
            try {
                Email email = new SimpleEmail();
                email.setHostName("smtp.gmail.com");
                email.setSmtpPort(587);
                email.setAuthenticator(new DefaultAuthenticator("trabajoPracticoPWB@gmail.com", "TrabajoPracticoBackEnd123!"));
                email.setSSLOnConnect(true);
                email.setFrom("poliredmine@gmail.com");
                email.setSubject("Uso de puntos");
                email.setMsg("Usted ha accedido al canje de puntos :-)");

                email.addTo(usoPunto.getCliente().getEmail());
                email.send();
            } catch (EmailException e) {
                e.printStackTrace();
            }
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
        bolsaPuntoDAO.actualizar(bp);
        detalleUsoPunto.setUsoPunto(usoPunto);
        detalleUsoPunto.setPuntajeUtilizado(saldo_punto_utilizado);
        detalleUsoPunto.setBolsa(bp);
        em.persist(detalleUsoPunto);

        return puntos;
    }

    public List<UsoPunto> listar(int id_cliente, Date fecha, int id_concepto) throws Exception{
        if (id_cliente != 0 && fecha != null && id_concepto != 0 ){
            Query q = em.createQuery(
                "select up from UsoPunto up join ConceptoPunto cp join Cliente c where up.fecha=:fecha and cp.id=:idConcepto and c.id=:idCliente");
            q.setParameter("idCliente", id_cliente);
            q.setParameter("fecha", fecha);
            q.setParameter("idConcepto", id_concepto);

            return (List<UsoPunto>) q.getResultList();
        }
        else if ( id_cliente == 0 && fecha == null && id_concepto != 0 ){
            Query q = em.createQuery("select up from UsoPunto up join ConceptoPunto cp where cp.id=:idConcepto");
            q.setParameter("idConcepto", id_concepto);

            return (List<UsoPunto>) q.getResultList();
        }
        else if ( id_cliente == 0 && fecha != null && id_concepto == 0 ){
            Query q = em.createQuery("select up from UsoPunto up where up.fecha=:fecha");
            q.setParameter("fecha", fecha);

            return (List<UsoPunto>) q.getResultList();
        }
        else if ( id_cliente == 0 && fecha != null && id_concepto != 0 ){
            Query q = em.createQuery("select up from UsoPunto up join ConceptoPunto cp where up.fecha=:fecha and cp.id=:idConcepto");
            q.setParameter("fecha", fecha);
            q.setParameter("idConcepto", id_concepto);

            return (List<UsoPunto>) q.getResultList();
        }
        else if ( id_cliente != 0 && fecha == null && id_concepto == 0 ){
            Query q = em.createQuery("select up from UsoPunto up join Cliente c where c.id=:idCliente");
            q.setParameter("idCliente", id_cliente);

            return (List<UsoPunto>) q.getResultList();
        }
        else if ( id_cliente != 0 && fecha == null && id_concepto != 0 ){
            Query q = em.createQuery("select up from UsoPunto up join Cliente c join ConceptoPunto cp where c.id=:idCliente and cp.id=:idConcepto");
            q.setParameter("idCliente", id_cliente);
            q.setParameter("idConcepto", id_concepto);
            
            return (List<UsoPunto>) q.getResultList();
        }
        else if ( id_cliente != 0 && fecha != null && id_concepto == 0 ){
            Query q = em.createQuery("select up from UsoPunto up join Cliente c where c.id=:idCliente and up.fecha=:fecha");
            q.setParameter("idCliente", id_cliente);
            q.setParameter("fecha", fecha);
            
            return (List<UsoPunto>) q.getResultList();
        }
        else{
            throw new Exception("PARAMETROS NULOS");
        }
    }
}
