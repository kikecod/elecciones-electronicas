package com.eleccioneselectronicas.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistribucionRecintoDTO {
    private String ci;
    private String nombrePersona;
    private String apellidoPersona;
    private String nombreRecinto;
    private String campus;
    private String edificio;
    private String aula;
    private String modelodispositivo;
}
