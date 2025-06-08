package com.eleccioneselectronicas.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "facultad")
public class Facultad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_facultad")
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "decano", referencedColumnName = "id_persona", nullable = false)
    private Docente decano;

    @Column(nullable = false)
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

    public Docente getDecano() {
        return decano;
    }

    public void setDecano(Docente decano) {
        this.decano = decano;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
