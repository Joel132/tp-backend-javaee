package com.example.bolsa_puntos.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Singleton
@Startup
public class AutomaticTaskBean {

    @PersistenceContext(name = "pruebaPU")
    EntityManager em;

    Logger log = LoggerFactory.getLogger(AutomaticTaskBean.class);


    @PostConstruct
    public void onStartup() {
        System.out.println("Initialization success.");
    }


    @Schedule(minute = "*/3", hour = "*", persistent = false)
    public void revisarVencidos(){
        Query q = em.createQuery("update BolsaPunto set saldo=0 where fechaVencimiento<CURRENT_DATE and saldo>0");
        int rs = q.executeUpdate();
        log.info(String.format("Se han actualizado %d registros", rs));

    }
}
