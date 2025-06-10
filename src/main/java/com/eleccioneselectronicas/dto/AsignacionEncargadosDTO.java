package com.eleccioneselectronicas.dto;

import java.util.List;

public class AsignacionEncargadosDTO {


    private Long recintoId;
    private Long docenteId;
    private List<Long> estudiantesIds;
    private Long eleccionId; // ✅ Campo necesario para tu lógica

    public Long getRecintoId() {
        return recintoId;
    }

    public void setRecintoId(Long recintoId) {
        this.recintoId = recintoId;
    }

    public Long getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(Long docenteId) {
        this.docenteId = docenteId;
    }

    public List<Long> getEstudiantesIds() {
        return estudiantesIds;
    }

    public void setEstudiantesIds(List<Long> estudiantesIds) {
        this.estudiantesIds = estudiantesIds;
    }

    public Long getEleccionId() {
        return eleccionId;
    }

    public void setEleccionId(Long eleccionId) {
        this.eleccionId = eleccionId;
    }
}
