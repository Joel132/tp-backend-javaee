package com.example.bolsa_puntos.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "detalle_uso_puntos")
public class DetalleUsoPunto {
    @Id
    @GeneratedValue(generator = "detalleSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "detalleSec",sequenceName = "detalle_sec", allocationSize = 0)
    @Column(name = "id_detalle", nullable = false)
    private Integer id;

    @Column(name = "puntaje_utilizado", nullable = false)
    private Integer puntajeUtilizado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_bolsa", referencedColumnName = "id_bolsa")
    private BolsaPunto bolsa;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_uso", referencedColumnName = "id_uso")
    @JsonBackReference
    private UsoPunto usoPunto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(Integer puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }

    public BolsaPunto getBolsa() {
        return bolsa;
    }

    public void setBolsa(BolsaPunto bolsa) {
        this.bolsa = bolsa;
    }

    public UsoPunto getUsoPunto() {
        return usoPunto;
    }

    public void setUsoPunto(UsoPunto usoPunto) {
        this.usoPunto = usoPunto;
    }
}
