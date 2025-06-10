package com.eleccioneselectronicas.dto;

public class EncargadoResponseDTO {
    private String ci;
    private String nombre;
    private String recinto;

    public EncargadoResponseDTO() {}

    public EncargadoResponseDTO(String ci, String nombre, String recinto) {
        this.ci = ci;
        this.nombre = nombre;
        this.recinto = recinto;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRecinto() {
        return recinto;
    }

    public void setRecinto(String recinto) {
        this.recinto = recinto;
    }
}
