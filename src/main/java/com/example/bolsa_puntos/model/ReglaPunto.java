package com.example.bolsa_puntos.model;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;

@Table(name = "regla_puntos")
@Entity
public class ReglaPunto {

    @Id
    @Column(name = "id_regla",nullable = false)
    @SequenceGenerator(name = "reglaPuntoSec",sequenceName = "regla_sec",allocationSize = 0)
    @GeneratedValue(generator = "reglaPuntoSec",strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "limite_inferior")
    private Integer limiteInferior;

    @Column(name = "limite_superior")
    private Integer limiteSuperior;

    @Column(name = "equivalencia",nullable = false)
    private Integer equivalencia;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLimiteInferior() {
        return limiteInferior;
    }

    public void setLimiteInferior(Integer limiteInferior) {
        this.limiteInferior = limiteInferior;
    }

    public Integer getLimiteSuperior() {
        return limiteSuperior;
    }

    public void setLimiteSuperior(Integer limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
    }

    public Integer getEquivalencia() {
        return equivalencia;
    }

    public void setEquivalencia(Integer equivalencia) {
        this.equivalencia = equivalencia;
    }

    public Integer puntosConseguidos(Integer monto){
        int lInf = limiteInferior == null ? -1 : limiteInferior.intValue();
        int lSup = limiteSuperior == null ? Integer.MAX_VALUE : limiteSuperior.intValue();
        int mont = monto.intValue();
        if (lInf <= monto  && lSup >= monto){
            return monto/this.getEquivalencia();
        }
        return 0;
    }
}
