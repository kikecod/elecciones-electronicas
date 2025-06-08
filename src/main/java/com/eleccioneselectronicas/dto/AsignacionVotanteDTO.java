package com.eleccioneselectronicas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AsignacionVotanteDTO {
    private Long idVotante;
    private Long idRecinto;
    private Long idDispositivo;
}
