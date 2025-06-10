package com.eleccioneselectronicas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultadosCandidatoDTO {
    private String nombrePartido;
    private String nombreCandidato;
    private int votos;
}