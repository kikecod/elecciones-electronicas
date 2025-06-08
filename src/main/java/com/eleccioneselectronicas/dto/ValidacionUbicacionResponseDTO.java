package com.eleccioneselectronicas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidacionUbicacionResponseDTO {
    private boolean validacion;
    private String mensaje;
}
