package com.eleccioneselectronicas.dto;

import lombok.Data;

@Data
public class ValidacionUbicacionRequestDTO {
    private String recinto;
    private Long idDispositivo;
}
