package com.eleccioneselectronicas.dto;

public class EncargadoInfoDTO {

    private String ci;
    private String nombreCompleto;
    private String recinto;

    public EncargadoInfoDTO() {}

    public EncargadoInfoDTO(String ci, String nombreCompleto, String recinto) {
        this.ci = ci;
        this.nombreCompleto = nombreCompleto;
        this.recinto = recinto;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getRecinto() {
        return recinto;
    }

    public void setRecinto(String recinto) {
        this.recinto = recinto;
    }
}
