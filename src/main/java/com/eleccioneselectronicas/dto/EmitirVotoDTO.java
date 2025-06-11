package com.eleccioneselectronicas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmitirVotoDTO {

    @NotNull(message = "El ID del votante no puede ser nulo")
    private Long idVotante;

    @NotNull(message = "El ID de la elección no puede ser nulo")
    private Long idEleccion;

    @NotNull(message = "El ID del partido no puede ser nulo")
    private Long idPartido;
}
