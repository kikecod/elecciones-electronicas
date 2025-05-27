package com.eleccioneselectronicas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

@Entity
@Table(name = "docente")
@PrimaryKeyJoinColumn(name = "id_docente")
public class Docente extends Persona {

    @NotBlank
    private String categoriaDocente;

    @NotBlank
    private String gradoAcademico;

    @Past
    private LocalDate fechaIngreso;

    private Boolean activo;

    public String getCategoriaDocente() {
        return categoriaDocente;
    }

    public void setCategoriaDocente(String categoriaDocente) {
        this.categoriaDocente = categoriaDocente;
    }

    public String getGradoAcademico() {
        return gradoAcademico;
    }

    public void setGradoAcademico(String gradoAcademico) {
        this.gradoAcademico = gradoAcademico;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
