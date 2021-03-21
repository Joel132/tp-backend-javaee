package com.example.bolsa_puntos.model;


import javax.persistence.*;

@Entity
@Table(name = "concepto_puntos")
public class ConceptoPunto {

    @Id
    @Column(name = "id_vigencia", nullable = false)
    @GeneratedValue(generator = "vigenciaSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "vigenciaSec", sequenceName = "vigencia_sec")
    private Integer id;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "puntos_requerido", nullable = false)
    private Integer puntosRequerido;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPuntosRequerido() {
        return puntosRequerido;
    }

    public void setPuntosRequerido(Integer puntosRequerido) {
        this.puntosRequerido = puntosRequerido;
    }

    public boolean isAvailable(int puntos){
        return puntosRequerido.intValue()<=puntos;
    }
}
