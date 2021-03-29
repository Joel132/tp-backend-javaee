package com.example.bolsa_puntos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(generator = "clienteSec",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "cliente_sec",name="clienteSec",allocationSize = 0)
    @Column(name = "id_cliente",nullable = false)
    private Integer id;

    @Column(name = "nombre",nullable = false)
    @NotNull
    @NotBlank
    private String nombre;

    @Column(name = "apellido",nullable = false)
    @NotNull
    @NotBlank
    private String apellido;

    @Column(name = "nro_documento",nullable = false)
    @NotNull
    @Positive
    private Integer nroDocumento;

    @Column(name = "tipo_documento",nullable = false)
    @NotNull
    @NotBlank
    private String tipoDocumento;

    @Column(name = "nacionalidad",nullable = false)
    @NotNull
    @NotBlank
    private String nacionalidad;

    @Column(name = "email",nullable = false)
    @NotNull
    @Email
    private String email;

    @Column(name = "telefono",nullable = false)
    @NotNull
    @NotBlank
    private String telefono;

    @Column(name="fecha_nacimiento",nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull
    private Date fechaNacimiento;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private Long puntos;
//    @OneToMany(mappedBy = "cliente")
//    @JsonManagedReference
//    private List<BolsaPunto> puntos=new ArrayList<>();
//
//    @OneToMany(mappedBy = "cliente")
//    @JsonManagedReference
//    private List<UsoPunto> usoPuntos=new ArrayList<>();


    public Cliente() {
    }

    public Cliente(Integer id, @NotNull @NotBlank String nombre, @NotNull @NotBlank String apellido, @NotNull @Positive Integer nroDocumento, @NotNull @NotBlank String tipoDocumento, @NotNull @NotBlank String nacionalidad, @NotNull @Email String email, @NotNull @NotBlank String telefono, @NotNull Date fechaNacimiento, Long puntos) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nroDocumento = nroDocumento;
        this.tipoDocumento = tipoDocumento;
        this.nacionalidad = nacionalidad;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.puntos = puntos;
    }

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

//    public List<BolsaPunto> getPuntos() {
//        return puntos;
//    }
//
//    public void setPuntos(List<BolsaPunto> puntos) {
//        this.puntos = puntos;
//    }
//
//    public List<UsoPunto> getUsoPuntos() {
//        return usoPuntos;
//    }
//
//    public void setUsoPuntos(List<UsoPunto> usoPuntos) {
//        this.usoPuntos = usoPuntos;
//    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Long getPuntos() {
        return puntos;
    }

    public void setPuntos(Long puntos) {
        this.puntos = puntos;
    }
}
