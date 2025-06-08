package com.eleccioneselectronicas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class VotoDTO {

    private Long idVoto;

    @NotNull
    private Long idVotante;

    @NotNull
    private Long idEleccion;

    @NotNull
    private Long idPartido;

    @NotNull
    private Long idRecinto;

    @NotNull
    private Long idDispositivo;

    private LocalDateTime emitidoTs;
    private String hashPrev;
    private String hashActual;
    private String folioRecibo;
    private Boolean valido;

    // Getters y setters
}
