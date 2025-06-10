package com.eleccioneselectronicas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "encargado")
public class Encargado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "recinto_id", nullable = false)
    private Recinto recinto;

    @ManyToOne
    @JoinColumn(name = "id_eleccion") // este será el nombre de la columna en la tabla
    private Eleccion eleccion;

    private String rol; // "DOCENTE" o "ESTUDIANTE"

    public Encargado() {}

    public Encargado(Long id, Persona persona, Recinto recinto, String rol, Eleccion eleccion) {
        this.id = id;
        this.persona = persona;
        this.recinto = recinto;
        this.rol = rol;
        this.eleccion = eleccion;
    }

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

    public Recinto getRecinto() {
        return recinto;
    }

    public void setRecinto(Recinto recinto) {
        this.recinto = recinto;
    }

    public Eleccion getEleccion() {
        return eleccion;
    }

    public void setEleccion(Eleccion eleccion) {
        this.eleccion = eleccion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
