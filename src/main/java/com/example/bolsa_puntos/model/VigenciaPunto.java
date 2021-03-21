package com.example.bolsa_puntos.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "vigencia_puntos")
@Entity
public class VigenciaPunto {

    @Id
    @Column(name = "id_vigencia", nullable = false)
    @GeneratedValue(generator = "vigenciaSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "vigenciaSec", sequenceName = "vigencia_sec")
    private Integer id;

    @Column(name="fecha_inicio",nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaInicio;

    @Column(name="fecha_fin",nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaFin;

    @Column(name = "duracion_dias", nullable = false)
    private Integer duracion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public Boolean isValid(Date fecha){
        return true;
    }
}
