package com.eleccioneselectronicas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultadosDispositivoDTO {
    private String nombreDispositivo;
    private List<ResultadosCandidatoDTO> resultadosCandidatos;
}

