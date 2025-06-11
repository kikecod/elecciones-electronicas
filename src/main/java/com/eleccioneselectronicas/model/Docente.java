package com.eleccioneselectronicas.model;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "docente")
public class Docente extends Persona {

    @Column(name = "categoria_docente", nullable = false, length = 100)
    @Basic(optional = false)
    @Length(min = 3, max = 100)
    private String categoriaDocente;

    @Column(name = "grado_academico", nullable = false, length = 100)
    @Basic(optional = false)
    @Length(min = 3, max = 100)
    private String gradoAcademico;

    @Column(name = "fecha_ingreso", nullable = false)
    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    private LocalDate fechaIngreso;

    @Column(name = "activo")
    private String activo;

    @OneToMany(mappedBy = "persona", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private java.util.List<PersonaCarrera> personaCarreras;
}
