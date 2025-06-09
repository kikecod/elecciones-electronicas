package com.eleccioneselectronicas.dto;

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

    // Ojo: ya no validamos qrUuid en la entrada; se genera en el backend
    private String qrUuid;

    // Desactivamos hashHuella y hashFirma (se pueden dejar sin validación o eliminarlos)
    private String hashHuella;
    private String hashFirma;

    @NotNull(message = "La imagen del rostro no puede estar vacía")
    private String hashRostro;

    private Boolean habilitado;
    private LocalDateTime fechaHabilitacion;
}
