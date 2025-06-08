package com.eleccioneselectronicas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ResultadosDTO {

    private Long idResultados;

    @NotNull
    private Long idEleccion;

    @NotNull
    private Long idCandidato;

    private Integer totalVotos;
    private Double porcentaje;

    private LocalDate fechaGeneracion;
    private LocalTime horaGeneracion;

    private Boolean esFinal;
    private String firma;
}
