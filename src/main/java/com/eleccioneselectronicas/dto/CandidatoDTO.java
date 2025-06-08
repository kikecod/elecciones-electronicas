package com.eleccioneselectronicas.dto;

import java.time.LocalDate;

public class CandidatoDTO {

    private Long idCandidato;
    private Long idPersona;
    private Long idPartido;
    private Long idEleccion;
    private String cargoPostulado;
    private Integer numeroLista;
    private Boolean esCabezaLista;
    private String lema;
    private String planPropuesta;
    private LocalDate fechaPostulacion;
    private Boolean estado;

    public Long getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Long idCandidato) {
        this.idCandidato = idCandidato;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Long getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(Long idPartido) {
        this.idPartido = idPartido;
    }

    public Long getIdEleccion() {
        return idEleccion;
    }

    public void setIdEleccion(Long idEleccion) {
        this.idEleccion = idEleccion;
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
