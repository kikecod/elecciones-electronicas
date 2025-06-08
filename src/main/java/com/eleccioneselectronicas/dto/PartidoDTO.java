package com.eleccioneselectronicas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PartidoDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    private String sigla;

    private String colorHex;
    private String logoUrl;

    @NotNull
    private Long representanteId;

    private String descripcion;

    // Getters and Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Long getRepresentanteId() {
        return representanteId;
    }

    public void setRepresentanteId(Long representanteId) {
        this.representanteId = representanteId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
