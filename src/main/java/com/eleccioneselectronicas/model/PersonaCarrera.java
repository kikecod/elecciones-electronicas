package com.eleccioneselectronicas.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@IdClass(PersonaCarreraId.class)
public class PersonaCarrera {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_persona")
    private Persona persona;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_carrera")
    private Carrera carrera;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    private LocalDate fechaAsignacion;

    private Boolean activo;

    public enum Rol {
        DOCENTE,
        ESTUDIANTE
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
