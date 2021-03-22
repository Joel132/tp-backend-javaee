package com.example.bolsa_puntos.model;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.lang.annotation.*;

@Table(name = "regla_puntos")
@Entity
@IntervaloCorrecto(message = "Limite Superior debe ser mayor o igual al limite inferior")
public class ReglaPunto {

    @Id
    @Column(name = "id_regla",nullable = false)
    @SequenceGenerator(name = "reglaPuntoSec",sequenceName = "regla_sec",allocationSize = 0)
    @GeneratedValue(generator = "reglaPuntoSec",strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "limite_inferior")
    @PositiveOrZero
    private Integer limiteInferior;

    @Column(name = "limite_superior")
    @Positive
    private Integer limiteSuperior;

    @Column(name = "equivalencia",nullable = false)
    @NotNull
    @Positive
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

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { IntervaloCorrectoValidator.class })
@Documented
 @interface IntervaloCorrecto {

    String message() default "{org.hibernate.validator.referenceguide.chapter06.classlevel." +
            "IntervaloCorrecto.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}

class IntervaloCorrectoValidator
        implements ConstraintValidator<IntervaloCorrecto, ReglaPunto> {

    @Override
    public void initialize(IntervaloCorrecto constraintAnnotation) {
    }

    @Override
    public boolean isValid(ReglaPunto regla, ConstraintValidatorContext context) {
        if ( regla == null || regla.getLimiteInferior()==null || regla.getLimiteSuperior()==null) {
            return true;
        }
        return regla.getLimiteSuperior().intValue()>=regla.getLimiteInferior().intValue();
    }
}