package com.example.bolsa_puntos.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "concepto_puntos")
public class ConceptoPunto {

    @Id
    @Column(name = "id_concepto", nullable = false)
    @GeneratedValue(generator = "conceptoSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "conceptoSec", sequenceName = "concepto_sec",allocationSize = 0)
    private Integer id;

    @Column(name = "descripcion", nullable = false)
    @NotNull
    @NotBlank
    private String descripcion;

    @Column(name = "puntos_requerido", nullable = false)
    @Positive
    @NotNull
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
