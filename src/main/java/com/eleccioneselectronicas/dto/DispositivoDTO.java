package com.eleccioneselectronicas.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DispositivoDTO {

    private Long id;

    @NotBlank
    private String serie;

    private String modelo;

    @NotNull
    private Long idRecinto;

    @NotBlank
    private String clavePublica;

    private LocalDate ultimaRevision;

    private Boolean estado;
    
}
