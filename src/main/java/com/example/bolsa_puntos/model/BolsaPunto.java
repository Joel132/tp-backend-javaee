package com.example.bolsa_puntos.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "bolsa_puntos")
@Entity
public class BolsaPunto {

    @Id
    @GeneratedValue(generator = "bolsaSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "bolsaSec",sequenceName = "bolsa_sec", allocationSize = 0)
    @Column(name = "id_bolsa", nullable = false)
    private Integer id;

    @Column(name = "fecha_asignacion", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaAsignacion;

    @Column(name = "fecha_vencimiento", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaVencimiento;

    @Column(name = "puntaje_utilizado", nullable = false)
    private Integer puntajeUtilizado;

    @Column(name = "puntaje_asignado", nullable = false)
    private Integer puntajeAsignado;

    @Column(name = "saldo", nullable = false)
    private Integer saldo;

    @Column(name = "monto", nullable = false)
    private Integer monto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @JsonBackReference
    private Cliente cliente;





    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Integer getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(Integer puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }

    public Integer getPuntajeAsignado() {
        return puntajeAsignado;
    }

    public void setPuntajeAsignado(Integer puntajeAsignado) {
        this.puntajeAsignado = puntajeAsignado;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

}
