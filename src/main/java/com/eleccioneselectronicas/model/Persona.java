package com.eleccioneselectronicas.model;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persona {
    @Id
    @Column(name = "id_persona")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersona;

    @Column(nullable = false, unique = true)
    @Basic(optional = false)
    private String ci;


    @Column(nullable = false, length = 50)
    @Basic(optional = false)
    @Length(min = 3, max = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    @Length(min = 3, max = 50)
    @Basic(optional = false)
    private String apellido_paterno;

    @Column(nullable = false, length = 50)
    @Length(min = 3, max = 15)
    @Basic(optional = false)
    private String apellido_materno;

    @Column(name = "fecha_nacimiento", nullable = false)
    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false, unique = true)
    @Basic(optional = false)
    private String email;

    @Column(nullable = false, unique = true)
    @Basic(optional = false)
    private String telefono;

    @Column(nullable = false, length = 100)
    @Basic(optional = false)
    @Length(min = 3, max = 105)
    private String direccion;

    @Column(nullable = true, length = 10)
    //@Length(min = 3, max = 10)
    private String genero;

    @Column(name = "fecha_alta", nullable = false)
    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    private LocalDate fechaAlta;
}