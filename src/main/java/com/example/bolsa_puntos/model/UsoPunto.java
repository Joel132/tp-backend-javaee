package com.example.bolsa_puntos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "uso_puntos")
public class UsoPunto {
    @Id
    @GeneratedValue(generator = "usoSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "usoSec",sequenceName = "uso_sec", allocationSize = 0)
    @Column(name = "id_uso", nullable = false)
    private Integer id;

    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "puntaje_utilizado", nullable = false)
    private Integer puntajeUtilizado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @JsonBackReference
    private Cliente cliente;

    @OneToMany(mappedBy = "usoPunto")
    @JsonManagedReference
    private List<DetalleUsoPunto> detalles;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_concepto", referencedColumnName = "id_concepto")
    private ConceptoPunto concepto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(Integer puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<DetalleUsoPunto> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleUsoPunto> detalles) {
        this.detalles = detalles;
    }

    public ConceptoPunto getConcepto() {
        return concepto;
    }

    public void setConcepto(ConceptoPunto concepto) {
        this.concepto = concepto;
    }
}