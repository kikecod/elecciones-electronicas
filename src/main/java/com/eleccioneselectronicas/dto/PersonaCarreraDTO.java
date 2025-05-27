package com.eleccioneselectronicas.dto;

import com.eleccioneselectronicas.model.PersonaCarrera.Rol;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class PersonaCarreraDTO {

    @NotNull(message = "El idPersona no puede ser nulo")
    private Long idPersona;

    @NotNull(message = "El idCarrera no puede ser nulo")
    private Long idCarrera;

    @NotNull(message = "El rol es obligatorio")
    private Rol rol;

    private LocalDate fechaAsignacion;

    private Boolean activo;

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Long getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Long idCarrera) {
        this.idCarrera = idCarrera;
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
