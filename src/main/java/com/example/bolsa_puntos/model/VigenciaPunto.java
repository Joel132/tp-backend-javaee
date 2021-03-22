package com.example.bolsa_puntos.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;
import java.util.Date;

@Table(name = "vigencia_puntos")
@Entity
@RangoCorrecto(message = "Fecha fin debe ser mayor o igual a la fecha inicial")
public class VigenciaPunto {

    @Id
    @Column(name = "id_vigencia", nullable = false)
    @GeneratedValue(generator = "vigenciaSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "vigenciaSec", sequenceName = "vigencia_sec",allocationSize = 0)
    private Integer id;

    @Column(name="fecha_inicio",nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull
    private Date fechaInicio;

    @Column(name="fecha_fin",nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull
    private Date fechaFin;

    @Column(name = "duracion_dias", nullable = false)
    @NotNull
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


@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { RangoCorrectoValidator.class })
@Documented
@interface RangoCorrecto {

    String message() default "{org.hibernate.validator.referenceguide.chapter06.classlevel." +
            "RangoCorrecto.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}

class RangoCorrectoValidator
        implements ConstraintValidator<RangoCorrecto, VigenciaPunto> {

    @Override
    public void initialize(RangoCorrecto constraintAnnotation) {
    }

    @Override
    public boolean isValid(VigenciaPunto vigenciaPunto, ConstraintValidatorContext context) {
        if ( vigenciaPunto == null ) {
            return true;
        }
        return vigenciaPunto.getFechaFin().compareTo(vigenciaPunto.getFechaInicio())>=0;
    }
}