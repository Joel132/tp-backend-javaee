package com.example.bolsa_puntos.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(generator = "clienteSec",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "cliente_sec",name="clienteSec",allocationSize = 0)
    @Column(name = "id_cliente",nullable = false)
    private Integer id;

    @Column(name = "apellido",nullable = false)
    private String nombre;

    @Column(name = "nro_documento",nullable = false)
    private Integer nroDocumento;

    @Column(name = "tipo_documento",nullable = false)
    private String tipoDocumento;

    @Column(name = "nacionalidad",nullable = false)
    private String nacionalidad;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "telefono",nullable = false)
    private String telefono;

    @Column(name="fecha_nacimiento",nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(Integer nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
