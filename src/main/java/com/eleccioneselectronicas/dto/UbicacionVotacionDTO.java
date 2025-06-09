package com.eleccioneselectronicas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UbicacionVotacionDTO {
    private String recinto;
    private String dispositivo;
}
