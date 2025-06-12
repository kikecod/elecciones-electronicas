package com.eleccioneselectronicas.dto;

import jakarta.validation.constraints.NotNull;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VotanteRequestDTO {
    private Long idVotante;
    private Long idPersona;
    private Long idEleccion;
    @NotNull(message = "La imagen del rostro no puede estar vacía")
    private String hashRostro;
}
