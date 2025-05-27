package com.eleccioneselectronicas.dto;

import java.time.LocalDate;

public class FacultadDTO {

    private Long id;
    private String nombre;
    private String codigo;
    private LocalDate fechaCreacion;
    private Long idDecano;
    private Boolean estado;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getIdDecano() {
        return idDecano;
    }

    public void setIdDecano(Long idDecano) {
        this.idDecano = idDecano;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
