package com.example.bolsa_puntos.ejb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        //Se traen todas las bolsas del cliente cuyo saldo sea mayor que 0
        Query q = em.createQuery("select bp from BolsaPunto bp where bp.cliente.id=:idCliente and bp.saldo>0 order by bp.fechaAsignacion, bp.id");
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

            rp.setMsg("UTILIZACI??N DE PUNTOS COMPLETADA");
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
        bp.usarPuntos(saldo_punto_utilizado);
        //BolsaDao actualizar;
        bolsaPuntoDAO.actualizar(bp);
        detalleUsoPunto.setUsoPunto(usoPunto);
        detalleUsoPunto.setPuntajeUtilizado(saldo_punto_utilizado);
        detalleUsoPunto.setBolsa(bp);
        em.persist(detalleUsoPunto);

        return puntos;
    }

    public List<UsoPunto> listar(int id_cliente, String fecha, int id_concepto) throws ParseException{

        if (id_cliente != 0 && fecha != null && id_concepto != 0 ){
            //Convertir Fecha- String a Date formato yyyy-MM-dd
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaUso = dateFormat.parse(fecha);
            Query q = em.createQuery(
                "select up from UsoPunto up where up.fecha=:fecha and up.concepto.id=:idConcepto and up.cliente.id=:idCliente");
            q.setParameter("idCliente", id_cliente);
            q.setParameter("fecha", fechaUso);
            q.setParameter("idConcepto", id_concepto);

            return (List<UsoPunto>) q.getResultList();
        }
        else if ( id_cliente == 0 && fecha == null && id_concepto != 0 ){
            Query q = em.createQuery("select up from UsoPunto up where up.concepto.id=:idConcepto");
            q.setParameter("idConcepto", id_concepto);

            return (List<UsoPunto>) q.getResultList();
        }
        else if ( id_cliente == 0 && fecha != null && id_concepto == 0 ){
            //Convertir Fecha- String a Date formato yyyy-MM-dd
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaUso = dateFormat.parse(fecha);
            Query q = em.createQuery("select up from UsoPunto up where up.fecha=:fecha");
            q.setParameter("fecha", fechaUso);

            return (List<UsoPunto>) q.getResultList();
        }
        else if ( id_cliente == 0 && fecha != null && id_concepto != 0 ){
            //Convertir Fecha- String a Date formato yyyy-MM-dd
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaUso = dateFormat.parse(fecha);
            Query q = em.createQuery("select up from UsoPunto up where up.fecha=:fecha and up.concepto.id=:idConcepto");
            q.setParameter("fecha", fechaUso);
            q.setParameter("idConcepto", id_concepto);

            return (List<UsoPunto>) q.getResultList();
        }
        else if ( id_cliente != 0 && fecha == null && id_concepto == 0 ){
            Query q = em.createQuery("select up from UsoPunto up where up.cliente.id=:idCliente");
            q.setParameter("idCliente", id_cliente);

            return (List<UsoPunto>) q.getResultList();
        }
        else if ( id_cliente != 0 && fecha == null && id_concepto != 0 ){
            Query q = em.createQuery("select up from UsoPunto up where up.cliente.id=:idCliente and up.concepto.id=:idConcepto");
            q.setParameter("idCliente", id_cliente);
            q.setParameter("idConcepto", id_concepto);
            
            return (List<UsoPunto>) q.getResultList();
        }
        else if ( id_cliente != 0 && fecha != null && id_concepto == 0 ){
            //Convertir Fecha- String a Date formato yyyy-MM-dd
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaUso = dateFormat.parse(fecha);
            Query q = em.createQuery("select up from UsoPunto up join Cliente c where c.id=:idCliente and up.fecha=:fecha");
            q.setParameter("idCliente", id_cliente);
            q.setParameter("fecha", fechaUso);
            
            return (List<UsoPunto>) q.getResultList();
        }
        else{
            Query q = em.createQuery("select up from UsoPunto up");
            return (List<UsoPunto>) q.getResultList();
        }
    }
}
