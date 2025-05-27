package com.eleccioneselectronicas.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "estudiante")
@PrimaryKeyJoinColumn(name = "id_estudiante")
public class Estudiante extends Persona {

    @Column(name = "matricula", unique = true, nullable = false)
    private String matricula;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(name = "semestre_actual", nullable = false)
    private Integer semestreActual;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    // Getters y Setters

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getSemestreActual() {
        return semestreActual;
    }

    public void setSemestreActual(Integer semestreActual) {
        this.semestreActual = semestreActual;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
