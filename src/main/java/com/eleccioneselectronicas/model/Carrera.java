package com.eleccioneselectronicas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "carrera")
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrera")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_facultad", referencedColumnName = "id_facultad")
    private Facultad facultad;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(name = "duracion_semestres", nullable = false)
    private Integer duracionSemestres;

    @Column(nullable = false)
    private Boolean estado;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Facultad getFacultad() {
        return facultad;
    }

    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
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

    public Integer getDuracionSemestres() {
        return duracionSemestres;
    }

    public void setDuracionSemestres(Integer duracionSemestres) {
        this.duracionSemestres = duracionSemestres;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
