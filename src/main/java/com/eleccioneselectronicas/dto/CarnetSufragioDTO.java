package com.eleccioneselectronicas.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CarnetSufragioDTO {
    private String nombreEleccion;
    private String fechaEmision;
    private String nombres;
    private String apellidos;
    private String fechaNacimiento;
    private String email;
    private String recinto;
    private String dispositivo;
    private String qrUuid;
    private String foto;
    private String ci;
    // Métodos getter y setter
}
