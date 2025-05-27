package com.eleccioneselectronicas.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import jakarta.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "estudiante")
public class Estudiante extends Persona {
    
    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    private LocalDate fechaIngreso;

    @Column(name = "semestre_actual", nullable = false)
    @Basic(optional = false)
    private Integer semestreActual;
    
    @Column(name = "estado")
    private String estado;

}