package com.eleccioneselectronicas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class VotanteDTO {

    private Long idVotante;

    @NotNull(message = "El ID de persona no puede ser nulo")
    private Long idPersona;

    @NotNull(message = "El ID de elección no puede ser nulo")
    private Long idEleccion;

    @NotNull(message = "El ID de recinto no puede ser nulo")
    private Long idRecinto;

    @NotBlank(message = "El QR UUID no puede estar vacío")
    private String qrUuid;

    private String hashHuella;
    private String hashFirma;
    private String hashRostro;

    private Boolean habilitado;
    private LocalDateTime fechaHabilitacion;

    // Getters y setters
}