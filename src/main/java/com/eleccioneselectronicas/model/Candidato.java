package com.eleccioneselectronicas.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "candidato")
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_candidato")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_persona", nullable = false)
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "id_partido", nullable = false)
    private Partido partido;

    @ManyToOne
    @JoinColumn(name = "id_eleccion", nullable = false)
    private Eleccion eleccion;

    @Column(name = "cargo_postulado", nullable = false)
    private String cargoPostulado;

    @Column(name = "numero_lista", nullable = false)
    private Integer numeroLista;

    @Column(name = "es_cabeza_lista", nullable = false)
    private Boolean esCabezaLista;

    @Column(nullable = false)
    private String lema;

    @Column(name = "plan_propuesta", columnDefinition = "TEXT")
    private String planPropuesta;

    @Column(name = "fecha_postulacion", nullable = false)
    private LocalDate fechaPostulacion;

    @Column(nullable = false)
    private Boolean estado;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public Eleccion getEleccion() {
        return eleccion;
    }

    public void setEleccion(Eleccion eleccion) {
        this.eleccion = eleccion;
    }

    public String getCargoPostulado() {
        return cargoPostulado;
    }

    public void setCargoPostulado(String cargoPostulado) {
        this.cargoPostulado = cargoPostulado;
    }

    public Integer getNumeroLista() {
        return numeroLista;
    }

    public void setNumeroLista(Integer numeroLista) {
        this.numeroLista = numeroLista;
    }

    public Boolean getEsCabezaLista() {
        return esCabezaLista;
    }

    public void setEsCabezaLista(Boolean esCabezaLista) {
        this.esCabezaLista = esCabezaLista;
    }

    public String getLema() {
        return lema;
    }

    public void setLema(String lema) {
        this.lema = lema;
    }

    public String getPlanPropuesta() {
        return planPropuesta;
    }

    public void setPlanPropuesta(String planPropuesta) {
        this.planPropuesta = planPropuesta;
    }

    public LocalDate getFechaPostulacion() {
        return fechaPostulacion;
    }

    public void setFechaPostulacion(LocalDate fechaPostulacion) {
        this.fechaPostulacion = fechaPostulacion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
