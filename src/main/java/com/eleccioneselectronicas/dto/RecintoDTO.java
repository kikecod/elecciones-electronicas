package com.eleccioneselectronicas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecintoDTO {
    private Long id;

    @NotBlank(message= "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message= "El campus es obligatorio")
    private String campus;

    @NotBlank(message = "El edificio es obligatorio")
    private String edificio;

    @NotBlank(message = "El aula es obligatoria")
    private String aula;

    @Min(value = 1, message = "La capacidad debe ser mayor a 0")
    private int capacidad;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
    
}
