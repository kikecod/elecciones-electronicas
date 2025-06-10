package com.eleccioneselectronicas.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TotalVotosCandidatosDTO {
    private String nombrePartido;
    private String nombreCandidato;
    private int totalVotos;
}
